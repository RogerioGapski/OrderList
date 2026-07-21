package com.orderlist.api.controllers;

import com.orderlist.api.config.swagger.*;
import com.orderlist.api.model.dto.request.order.CreateOrderDTO;
import com.orderlist.api.model.dto.response.OrderDTO;
import com.orderlist.api.security.authorization.CurrentUserId;
import com.orderlist.api.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "jwtAuth")
@RequestMapping("/orders/user")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Creates order")
    @ApiProtectedCreateResponses
    @PostMapping("/{userId}")
    public ResponseEntity<OrderDTO> createOrder(
            @CurrentUserId UUID userId,
            @RequestBody @Valid CreateOrderDTO dto) {
        OrderDTO order = orderService.createOrder(dto, userId);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order.id())
                .toUri();
        return ResponseEntity.created(uri).body(order);
    }

    @Operation(summary = "Deletes order by ID")
    @ApiProtectedDeleteResponses
    @DeleteMapping("/{userId}/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable UUID userId,
            @PathVariable Long orderId) {
        orderService.deleteById(orderId, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Finds an user order by user ID and order ID")
    @ApiProtectedReadResponses
    @GetMapping("/{userId}/{orderId}")
    public ResponseEntity<OrderDTO> findById(
            @CurrentUserId UUID userId,
            @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.findById(orderId, userId));
    }

    @Operation(summary = "Finds for any request by ID")
    @ApiProtectedReadResponses
    @GetMapping("/admin/{userId}/{orderId}")
    public ResponseEntity<OrderDTO> findOrderAdmin(
            @PathVariable UUID userId,
            @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.findOrderByUser(orderId, userId));
    }

    @Operation(summary = "Removes an item from the order")
    @ApiProtectedDeleteResponses
    @DeleteMapping("/{userId}/{orderId}/{itemId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable UUID userId,
            @PathVariable Long orderId,
            @PathVariable Long itemId) {
        orderService.removeItem(orderId, itemId, userId);
        return ResponseEntity.noContent().build();
    }
}

