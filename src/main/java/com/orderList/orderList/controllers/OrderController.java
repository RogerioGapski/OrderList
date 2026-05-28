package com.orderList.orderList.controllers;

import com.orderList.orderList.model.dto.request.order.CreateOrderDTO;
import com.orderList.orderList.model.dto.response.OrderDTO;
import com.orderList.orderList.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            @PathVariable Long userId,
            @RequestBody @Valid CreateOrderDTO dto) {
        OrderDTO order = orderService.createOrder(dto, userId);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order.id())
                .toUri();
        return ResponseEntity.created(uri).body(order);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable Long userId,
            @PathVariable Long orderId) {
        orderService.deleteById(orderId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> findById(
            @PathVariable Long userId,
            @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.findById(orderId, userId));
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable Long userId,
            @PathVariable Long orderId,
            @PathVariable Long itemId) {
        orderService.removeItem(orderId, itemId, userId);
        return ResponseEntity.noContent().build();
    }
}

