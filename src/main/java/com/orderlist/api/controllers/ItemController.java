package com.orderlist.api.controllers;

import com.orderlist.api.model.dto.request.item.CreateItemDTO;
import com.orderlist.api.model.dto.request.item.UpdateItemQuantity;
import com.orderlist.api.model.dto.response.ItemDTO;
import com.orderlist.api.services.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/product/{productId}")
    public ResponseEntity<ItemDTO> createItem(
            @PathVariable UUID userId,
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
            @PathVariable UUID userId,
            @PathVariable Long itemId) {
        itemService.deleteById(itemId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDTO> findById(
            @PathVariable UUID userId,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.findById(itemId, userId));
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> findAll(@PathVariable UUID userId) {
        return ResponseEntity.ok(itemService.findAll(userId));
    }

    @PatchMapping("/{itemId}/increase")
    public ResponseEntity<ItemDTO> increaseQuantity(
            @PathVariable UUID userId,
            @PathVariable Long itemId,
            @RequestBody @Valid UpdateItemQuantity dto) {
        return ResponseEntity.ok(itemService.increaseQuantity(itemId, userId, dto));
    }

    @PatchMapping("/{itemId}/decrease")
    public ResponseEntity<ItemDTO> decreaseQuantity(
            @PathVariable UUID userId,
            @PathVariable Long itemId,
            @RequestBody @Valid UpdateItemQuantity dto) {
        return ResponseEntity.ok(itemService.decreaseQuantity(itemId, userId, dto));
    }

    @PatchMapping("/{itemId}/order/{orderId}")
    public ResponseEntity<Void> addToOrder(
            @PathVariable UUID userId,
            @PathVariable Long itemId,
            @PathVariable Long orderId) {
        itemService.addToOrder(itemId, orderId, userId);
        return ResponseEntity.noContent().build();
    }
}