package com.orderlist.api.services;

import com.orderlist.api.exceptions.customs.UnauthorizedException;
import com.orderlist.api.model.dto.request.order.CreateOrderDTO;
import com.orderlist.api.model.entities.Item;
import com.orderlist.api.model.entities.Order;
import com.orderlist.api.model.dto.response.OrderDTO;
import com.orderlist.api.exceptions.customs.BadRequestException;
import com.orderlist.api.exceptions.customs.NotFoundException;
import com.orderlist.api.model.entities.User;
import com.orderlist.api.model.enums.OrderStatus;
import com.orderlist.api.model.enums.Payments;
import com.orderlist.api.utils.mapper.ItemMapper;
import com.orderlist.api.utils.mapper.OrderMapper;
import com.orderlist.api.repository.ItemRepository;
import com.orderlist.api.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final ItemRepository itemRepository;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @Transactional
    @PreAuthorize("#userId == authentication.principal.user.id")
    public OrderDTO createOrder(CreateOrderDTO dto, UUID userId) {
        User user = userService.findUserById(userId);
        List<Item> pendingItems = itemRepository.findAllByUserIdNoPageable(userId).stream()
                .toList();

        if(pendingItems.isEmpty()){
            throw new BadRequestException("No items available to create and order.");
        }

        BigDecimal total = pendingItems.stream()
                .map(Item::getUnitaryPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String paymentType = dto.paymentType().toString().toUpperCase();
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setTotal(total);
        order.setItems(pendingItems);
        order.setPaymentType(Payments.valueOf(paymentType));
        orderRepository.save(order);

        pendingItems.forEach(item -> item.setOrder(order));
        itemRepository.saveAll(pendingItems);

        return orderMapper.toDTO(order);
    }

    @Transactional
    @PreAuthorize("#userId == authentication.principal.user.id")
    public void deleteById(Long orderId, UUID userId) {
        Order order = findOrderById(orderId);
        checkOwnership(order, userId);
        orderRepository.delete(order);
    }

    @PreAuthorize("#userId == authentication.principal.user.id")
    public OrderDTO findById(Long orderId, UUID userId) {
        Order order = findOrderById(orderId);
        checkOwnership(order, userId);
        return orderMapper.toDTO(order);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public OrderDTO findOrderByUser(Long orderId, UUID userId) {
        Order order = findOrderById(orderId);
        User user = userService.findUserById(userId);

        if(!order.getUser().getId().equals(user.getId())){
            throw new BadRequestException("The order doesn't belong to this user");
        }
        return orderMapper.toDTO(order);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.user.id")
    public void removeItem(Long orderId, Long itemId, UUID userId) {
        Order order = findOrderById(orderId);
        checkOwnership(order, userId);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found."));

        if(item.getOrder() == null || !item.getOrder().getId().equals(orderId)) {
            throw new BadRequestException("Items does not belong to this Order");
        }

        item.setOrder(null);
        itemRepository.save(item);
    }

    //Auxiliary methods
    Order findOrderById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    void checkOwnership(Order order, UUID userId){
        if(!order.getUser().getId().equals(userId)){
            throw new UnauthorizedException("Order does not belong to this user.");
        }
    }
}
