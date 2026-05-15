package com.orderList.orderList.services;

import com.orderList.orderList.model.entities.OrderItem;
import com.orderList.orderList.model.entities.Product;
import com.orderList.orderList.model.dto.request.CreateOrderItemDTO;
import com.orderList.orderList.model.dto.response.OrderItemDTO;
import com.orderList.orderList.exceptions.customs.BadRequestException;
import com.orderList.orderList.exceptions.customs.NotFoundException;
import com.orderList.orderList.utils.mapper.OrderItemMapper;
import com.orderList.orderList.repository.OrderItemRepository;
import com.orderList.orderList.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    public OrderItemDTO createOrderItem(CreateOrderItemDTO dto) {
        OrderItem orderItem = orderItemMapper.toEntity(dto);
        orderItemRepository.save(orderItem);
        return orderItemMapper.toDTO(orderItem);
    }

    @Transactional
    public void deleteById(Long orderItemId){
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("OrderItem not found"));

        orderItemRepository.delete(orderItem);
    }

    public OrderItemDTO findById(Long orderItemId){
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException("OrderItem not found"));

        return orderItemMapper.toDTO(orderItem);
    }

    @Transactional
    public OrderItemDTO changeQuantity(Long orderItemId, Long productId, Integer quantity){
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

    @Transactional
    public OrderItemDTO increaseQuantity(Long orderItemId, Long productId, Integer quantity) {
        return changeQuantity(orderItemId, productId, quantity);
    }

    @Transactional
    public OrderItemDTO decreaseQuantity(Long orderItemId, Long productId, Integer quantity){
        return changeQuantity(orderItemId, productId, -quantity);
    }
}
