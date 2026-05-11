package com.orderList.orderList.services;

import com.orderList.orderList.domain.entities.OrderItem;
import com.orderList.orderList.domain.entities.Product;
import com.orderList.orderList.dto.request.CreateOrderItemDTO;
import com.orderList.orderList.dto.response.OrderItemDTO;
import com.orderList.orderList.exceptions.BadRequestException;
import com.orderList.orderList.exceptions.NotFoundException;
import com.orderList.orderList.mapper.OrderItemMapper;
import com.orderList.orderList.repository.OrderItemRepository;
import com.orderList.orderList.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    public OrderItemDTO createOrderItem(CreateOrderItemDTO dto) {
        OrderItem orderItem = orderItemMapper.toEntity(dto);
        orderItemRepository.save(orderItem);
        return orderItemMapper.toDTO(orderItem);
    }

    public OrderItemDTO changeQuantity(Integer orderItemId, Integer productId, Integer quantity){
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("Order item not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if(product.getStock() < quantity){
            throw new BadRequestException("Not enough stock");
        }

        orderItem.setQuantity(orderItem.getQuantity() + quantity);
        product.setStock(product.getStock() - quantity);
        orderItem.setUnitary_price(orderItem.getUnitary_price() + (product.getPrice() * quantity));

        productRepository.save(product);
        orderItemRepository.save(orderItem);
        return orderItemMapper.toDTO(orderItem);
    }

    public OrderItemDTO increaseQuantity(Integer orderItemId, Integer productId, Integer quantity) {
        return changeQuantity(orderItemId, productId, quantity);
    }

    public OrderItemDTO decreaseQuantity(Integer orderItemId, Integer productId, Integer quantity){
        return changeQuantity(orderItemId, productId, -quantity);
    }

    public void deleteOrderItemById(Integer orderItemId){
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("OrderItem not found"));

        orderItemRepository.delete(orderItem);
    }

    public OrderItemDTO findOrderItemById(Integer orderItemId){
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("OrderItem not found"));

        return orderItemMapper.toDTO(orderItem);
    }
}
