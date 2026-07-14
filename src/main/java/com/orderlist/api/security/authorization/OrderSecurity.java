package com.orderlist.api.security.authorization;

import com.orderlist.api.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("orderSecurity")
@RequiredArgsConstructor
public class OrderSecurity {

    private final OrderRepository orderRepository;

    public boolean isOwner(Long orderId, UUID userId){
        return orderRepository.findById(orderId)
                .map(order -> order.getUser().getId().equals(userId))
                .orElse(false);
    }
}
