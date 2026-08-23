package com.orderlist.api.controllers;

import com.orderlist.api.config.swagger.*;
import com.orderlist.api.model.dto.request.item.CreateItemDTO;
import com.orderlist.api.model.dto.request.item.UpdateItemQuantity;
import com.orderlist.api.model.dto.response.ItemDTO;
import com.orderlist.api.security.authorization.CurrentUserId;
import com.orderlist.api.services.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "jwtAuth")
@RequestMapping("/users/items")
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "Creates a item by product ID")
    @ApiProtectedCreateResponses
    @PostMapping("/{productId}")
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

    @Operation(summary = "Delete an item by ID")
    @ApiProtectedDeleteResponses
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long itemId) {
        itemService.deleteById(itemId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Finds an item by ID")
    @ApiProtectedReadResponses
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDTO> findById(
            @PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.findById(itemId));
    }

    @Operation(summary = "Finds all user items by user ID")
    @ApiProtectedReadResponses
    @GetMapping("/all/{userId}")
    public ResponseEntity<Page<ItemDTO>> findAll(@PathVariable UUID userId, Pageable pageable) {
        return ResponseEntity.ok(itemService.findAll(userId, pageable));
    }

    @Operation(summary = "Increase the quantity of a product in an item by item ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "The requisition couldn't be completed"),
            @ApiResponse(responseCode = "403", description = "You don't have permission to perform this action"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    @PatchMapping("/increase/{itemId}")
    public ResponseEntity<ItemDTO> increaseQuantity(
            @PathVariable Long itemId,
            @RequestBody @Valid UpdateItemQuantity dto) {
        return ResponseEntity.ok(itemService.increaseQuantity(itemId, dto));
    }

    @Operation(summary = "Decrease the quantity of a product in an item by item ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "The requisition couldn't be completed"),
            @ApiResponse(responseCode = "403", description = "You don't have permission to perform this action"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    @PatchMapping("/decrease/{itemId}")
    public ResponseEntity<ItemDTO> decreaseQuantity(
            @PathVariable Long itemId,
            @RequestBody @Valid UpdateItemQuantity dto) {
        return ResponseEntity.ok(itemService.decreaseQuantity(itemId, dto));
    }

    @Operation(summary = "Adds the item to an order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "You don't have permission to perform this action"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    @PatchMapping("/{itemId}/order/{orderId}")
    public ResponseEntity<Void> addToOrder(
            @PathVariable Long itemId,
            @PathVariable Long orderId) {
        itemService.addToOrder(itemId, orderId);
        return ResponseEntity.noContent().build();
    }
}