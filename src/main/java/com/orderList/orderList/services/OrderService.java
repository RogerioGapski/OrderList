package com.orderList.orderList.services;

import com.orderList.orderList.model.dto.request.order.CreateOrderDTO;
import com.orderList.orderList.model.entities.Item;
import com.orderList.orderList.model.entities.Order;
import com.orderList.orderList.model.dto.response.OrderDTO;
import com.orderList.orderList.exceptions.customs.BadRequestException;
import com.orderList.orderList.exceptions.customs.NotFoundException;
import com.orderList.orderList.model.enums.OrderStatus;
import com.orderList.orderList.repository.UserRepository;
import com.orderList.orderList.utils.mapper.OrderMapper;
import com.orderList.orderList.repository.ItemsRepository;
import com.orderList.orderList.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final ItemsRepository itemsRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderDTO createOrder(CreateOrderDTO dto, Long userId) {
        Order order = orderMapper.toEntity(dto);
        order.setStatus(OrderStatus.PENDING);
        order.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found")));
        orderRepository.addItems(itemsRepository.findAll());
        orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    @Transactional
    public void deleteById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        orderRepository.delete(order);
    }

    public OrderDTO findById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        return orderMapper.toDTO(order);
    }

    @Transactional
    public void removeItems(Long orderId, Long itemsId) {
        Item item = itemsRepository.findById(itemsId)
                .orElseThrow(() -> new NotFoundException("Items not found"));

        if(item.getOrder() == null || !item.getOrder().getId().equals(orderId)) {
            throw new BadRequestException("Items does not belong to this Order");
        }

        item.setOrder(null);
        itemsRepository.save(item);
    }
}
