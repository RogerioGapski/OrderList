package com.orderlist.api.controllers;

import com.orderlist.api.model.dto.request.order.CreateOrderDTO;
import com.orderlist.api.model.dto.response.OrderDTO;
import com.orderlist.api.model.entities.Order;
import com.orderlist.api.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders/user")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<OrderDTO> createOrder(
            @AuthenticationPrincipal UUID userId,
            @RequestBody @Valid CreateOrderDTO dto) {
        OrderDTO order = orderService.createOrder(dto, userId);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order.id())
                .toUri();
        return ResponseEntity.created(uri).body(order);
    }

    @DeleteMapping("/{userId}/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable UUID userId,
            @PathVariable Long orderId) {
        orderService.deleteById(orderId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/{orderId}")
    public ResponseEntity<OrderDTO> findById(
            @AuthenticationPrincipal UUID userId,
            @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.findById(orderId, userId));
    }

    @GetMapping("/admin/{userId}/{orderId}")
    public ResponseEntity<OrderDTO> findOrderAdmin(
            @PathVariable UUID userId,
            @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.findOrderByUser(orderId, userId));
    }

    @DeleteMapping("/{userId}/{orderId}/{itemId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable UUID userId,
            @PathVariable Long orderId,
            @PathVariable Long itemId) {
        orderService.removeItem(orderId, itemId, userId);
        return ResponseEntity.noContent().build();
    }
}

