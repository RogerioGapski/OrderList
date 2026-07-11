package com.orderlist.api.controllers;

import com.orderlist.api.model.dto.request.item.CreateItemDTO;
import com.orderlist.api.model.dto.request.item.UpdateItemQuantity;
import com.orderlist.api.model.dto.response.ItemDTO;
import com.orderlist.api.security.CurrentUserId;
import com.orderlist.api.services.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/product/{productId}")
    public ResponseEntity<ItemDTO> createItem(
            @CurrentUserId UUID userId,
            @PathVariable Long productId,
            @RequestBody @Valid CreateItemDTO dto) {
        ItemDTO item = itemService.createItem(dto, productId, userId);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(item.id())
                .toUri();
        return ResponseEntity.created(uri).body(item);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @CurrentUserId UUID userId,
            @PathVariable Long itemId) {
        itemService.deleteById(itemId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/{itemId}")
    public ResponseEntity<ItemDTO> findById(
            @PathVariable UUID userId,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.findById(itemId, userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<ItemDTO>> findAll(@PathVariable UUID userId, Pageable pageable) {
        return ResponseEntity.ok(itemService.findAll(userId, pageable));
    }

    @PatchMapping("/{itemId}/increase")
    public ResponseEntity<ItemDTO> increaseQuantity(
            @CurrentUserId UUID userId,
            @PathVariable Long itemId,
            @RequestBody @Valid UpdateItemQuantity dto) {
        return ResponseEntity.ok(itemService.increaseQuantity(itemId, userId, dto));
    }

    @PatchMapping("/{itemId}/decrease")
    public ResponseEntity<ItemDTO> decreaseQuantity(
            @CurrentUserId UUID userId,
            @PathVariable Long itemId,
            @RequestBody @Valid UpdateItemQuantity dto) {
        return ResponseEntity.ok(itemService.decreaseQuantity(itemId, userId, dto));
    }

    @PatchMapping("/{itemId}/order/{orderId}")
    public ResponseEntity<Void> addToOrder(
            @CurrentUserId UUID userId,
            @PathVariable Long itemId,
            @PathVariable Long orderId) {
        itemService.addToOrder(itemId, orderId, userId);
        return ResponseEntity.noContent().build();
    }
}