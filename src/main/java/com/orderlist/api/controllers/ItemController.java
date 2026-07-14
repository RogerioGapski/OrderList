package com.orderlist.api.controllers;

import com.orderlist.api.model.dto.request.item.CreateItemDTO;
import com.orderlist.api.model.dto.request.item.UpdateItemQuantity;
import com.orderlist.api.model.dto.response.ItemDTO;
import com.orderlist.api.security.authorization.CurrentUserId;
import com.orderlist.api.services.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
            @PathVariable Long itemId) {
        itemService.deleteById(itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDTO> findById(
            @PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.findById(itemId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<ItemDTO>> findAll(@PathVariable UUID userId, Pageable pageable) {
        return ResponseEntity.ok(itemService.findAll(userId, pageable));
    }

    @PatchMapping("/increase/{itemId}")
    public ResponseEntity<ItemDTO> increaseQuantity(
            @PathVariable Long itemId,
            @RequestBody @Valid UpdateItemQuantity dto) {
        return ResponseEntity.ok(itemService.increaseQuantity(itemId, dto));
    }

    @PatchMapping("/decrease/{itemId}")
    public ResponseEntity<ItemDTO> decreaseQuantity(
            @PathVariable Long itemId,
            @RequestBody @Valid UpdateItemQuantity dto) {
        return ResponseEntity.ok(itemService.decreaseQuantity(itemId, dto));
    }

    @PatchMapping("/{itemId}/order/{orderId}")
    public ResponseEntity<Void> addToOrder(
            @PathVariable Long itemId,
            @PathVariable Long orderId) {
        itemService.addToOrder(itemId, orderId);
        return ResponseEntity.noContent().build();
    }
}