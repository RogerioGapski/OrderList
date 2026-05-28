package com.orderList.orderList.services;

import com.orderList.orderList.exceptions.customs.UnauthorizedException;
import com.orderList.orderList.model.dto.request.order.CreateOrderDTO;
import com.orderList.orderList.model.entities.Item;
import com.orderList.orderList.model.entities.Order;
import com.orderList.orderList.model.dto.response.OrderDTO;
import com.orderList.orderList.exceptions.customs.BadRequestException;
import com.orderList.orderList.exceptions.customs.NotFoundException;
import com.orderList.orderList.model.entities.User;
import com.orderList.orderList.model.enums.OrderStatus;
import com.orderList.orderList.utils.mapper.OrderMapper;
import com.orderList.orderList.repository.ItemRepository;
import com.orderList.orderList.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final ItemRepository itemRepository;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderDTO createOrder(CreateOrderDTO dto, Long userId) {
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
    public void deleteById(Long orderId, Long userId) {
        Order order = findOrderById(orderId);
        checkOwnership(order, userId);
        orderRepository.delete(order);
    }

    public OrderDTO findById(Long orderId, Long userId) {
        Order order = findOrderById(orderId);
        checkOwnership(order, userId);
        return orderMapper.toDTO(order);
    }

    @Transactional
    public void removeItems(Long orderId, Long itemId, Long userId) {
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

    private void checkOwnership(Order order, Long userId){
        if(!order.getUser().getId().equals(userId)){
            throw new UnauthorizedException("Order does not belong to this user.");
        }
    }
}
