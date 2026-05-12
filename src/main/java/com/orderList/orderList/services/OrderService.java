package com.orderList.orderList.services;

import com.orderList.orderList.domain.entities.Order;
import com.orderList.orderList.domain.entities.OrderItem;
import com.orderList.orderList.dto.request.CreateOrderDTO;
import com.orderList.orderList.dto.response.OrderDTO;
import com.orderList.orderList.exceptions.BadRequestException;
import com.orderList.orderList.exceptions.NotFoundException;
import com.orderList.orderList.mapper.OrderMapper;
import com.orderList.orderList.repository.OrderItemRepository;
import com.orderList.orderList.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderDTO createOrder(CreateOrderDTO dto) {
        Order order = orderMapper.toEntity(dto);
        orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

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

    public void addOrderItem(Long orderId, Long orderItemId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("Order item not found"));

        orderItem.setOrder(order);
        orderItemRepository.save(orderItem);
    }

    public void removeOrderItem(Long orderId, Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("Order item not found"));

        if(orderItem.getOrder() == null || !orderItem.getOrder().getId().equals(orderId)) {
            throw new BadRequestException("Order item does not belong to this Order");
        }

        orderItem.setOrder(null);
        orderItemRepository.save(orderItem);
    }
}
