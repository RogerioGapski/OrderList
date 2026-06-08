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
import com.orderlist.api.utils.mapper.OrderMapper;
import com.orderlist.api.repository.ItemRepository;
import com.orderlist.api.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public OrderDTO createOrder(CreateOrderDTO dto, UUID userId) {
        User user = userService.findUserById(userId);
        List<Item> pendingItems = itemRepository.findAllByUserId(userId).stream()
                .filter(i -> i.getOrder() == null)
                .toList();

        if(pendingItems.isEmpty()){
            throw new BadRequestException("No items avaliable to create and order.");
        }

        double total = pendingItems.stream()
                .mapToDouble(Item::getUnitaryPrice)
                .sum();

        Order order = orderMapper.toEntity(dto);
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setTotal(total);
        orderRepository.save(order);

        pendingItems.forEach(item -> item.setOrder(order));
        itemRepository.saveAll(pendingItems);

        return orderMapper.toDTO(order);
    }

    @Transactional
    public void deleteById(Long orderId, UUID userId) {
        Order order = findOrderById(orderId);
        checkOwnership(order, userId);
        orderRepository.delete(order);
    }

    public OrderDTO findById(Long orderId, UUID userId) {
        Order order = findOrderById(orderId);
        checkOwnership(order, userId);
        return orderMapper.toDTO(order);
    }

    @Transactional
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

    private Order findOrderById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    private void checkOwnership(Order order, UUID userId){
        if(!order.getUser().getId().equals(userId)){
            throw new UnauthorizedException("Order does not belong to this user.");
        }
    }
}
