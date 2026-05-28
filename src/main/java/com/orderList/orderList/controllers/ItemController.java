package com.orderList.orderList.controllers;

import com.orderList.orderList.model.dto.request.item.CreateItemDTO;
import com.orderList.orderList.model.dto.request.item.UpdateItemQuantity;
import com.orderList.orderList.model.dto.response.ItemDTO;
import com.orderList.orderList.services.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/product/{productId}")
    public ResponseEntity<ItemDTO> createItem(
            @PathVariable Long userId,
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
            @PathVariable Long userId,
            @PathVariable Long itemId) {
        itemService.deleteById(itemId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDTO> findById(
            @PathVariable Long userId,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.findById(itemId, userId));
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> findAll(@PathVariable Long userId) {
        return ResponseEntity.ok(itemService.findAll(userId));
    }

    @PatchMapping("/{itemId}/increase")
    public ResponseEntity<ItemDTO> increaseQuantity(
            @PathVariable Long userId,
            @PathVariable Long itemId,
            @RequestBody @Valid UpdateItemQuantity dto) {
        return ResponseEntity.ok(itemService.increaseQuantity(itemId, userId, dto));
    }

    @PatchMapping("/{itemId}/decrease")
    public ResponseEntity<ItemDTO> decreaseQuantity(
            @PathVariable Long userId,
            @PathVariable Long itemId,
            @RequestBody @Valid UpdateItemQuantity dto) {
        return ResponseEntity.ok(itemService.decreaseQuantity(itemId, userId, dto));
    }

    @PatchMapping("/{itemId}/order/{orderId}")
    public ResponseEntity<Void> addToOrder(
            @PathVariable Long userId,
            @PathVariable Long itemId,
            @PathVariable Long orderId) {
        itemService.addToOrder(itemId, orderId, userId);
        return ResponseEntity.noContent().build();
    }
}