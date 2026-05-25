package com.orderList.orderList.controllers;

import com.orderList.orderList.model.dto.request.order.CreateOrderDTO;
import com.orderList.orderList.model.dto.response.OrderDTO;
import com.orderList.orderList.model.entities.Order;
import com.orderList.orderList.services.OrderService;
import com.orderList.orderList.utils.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<OrderDTO> createOrder(
            @PathVariable Long userId,
            @RequestBody CreateOrderDTO dto
    ){
        OrderDTO order = orderService.createOrder(dto, userId);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{orderId}")
                .buildAndExpand(order.id())
                .toUri();

        return ResponseEntity.created(uri).body(order);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable Long orderId
    ){
        orderService.deleteById(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(
            @PathVariable Long orderId
    ){
        return ResponseEntity.ok().body(orderService.findById(orderId));
    }

    @PatchMapping("/remove/{orderId}/{itemsId}")
    public ResponseEntity<Void> addItems(
            @PathVariable Long orderId,
            @PathVariable Long itemsId
    ){
        orderService.removeItems(orderId, itemsId);
        return ResponseEntity.noContent().build();
    }
}
