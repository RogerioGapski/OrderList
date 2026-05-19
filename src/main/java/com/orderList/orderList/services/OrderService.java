package com.orderList.orderList.services;

import com.orderList.orderList.model.entities.Items;
import com.orderList.orderList.model.entities.Order;
import com.orderList.orderList.model.dto.request.CreateOrderDTO;
import com.orderList.orderList.model.dto.response.OrderDTO;
import com.orderList.orderList.exceptions.customs.BadRequestException;
import com.orderList.orderList.exceptions.customs.NotFoundException;
import com.orderList.orderList.utils.mapper.OrderMapper;
import com.orderList.orderList.repository.ItemsRepository;
import com.orderList.orderList.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ItemsRepository itemsRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderDTO createOrder(CreateOrderDTO dto) {
        Order order = orderMapper.toEntity(dto);
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
    public void addOrderItem(Long orderId, Long orderItemId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        Items items = itemsRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("Order item not found"));

        items.setOrder(order);
        itemsRepository.save(items);
    }

    @Transactional
    public void removeOrderItem(Long orderId, Long orderItemId) {
        Items items = itemsRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("Order item not found"));

        if(items.getOrder() == null || !items.getOrder().getId().equals(orderId)) {
            throw new BadRequestException("Order item does not belong to this Order");
        }

        items.setOrder(null);
        itemsRepository.save(items);
    }
}
