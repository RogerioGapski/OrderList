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
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/{productId}")
    public ResponseEntity<ItemDTO> createItems(
            @PathVariable Long productId,
            @RequestBody @Valid CreateItemDTO dto
    ){
        ItemDTO items = itemService.createItems(dto, productId);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(items.id())
                .toUri();

        return ResponseEntity.created(uri).body(items);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItems(
            @PathVariable Long id
    ){
        itemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok().body(itemService.findById(id));
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<ItemDTO>> getAll(
            @PathVariable Long userId
    ){
        return ResponseEntity.ok().body(itemService.findAll(userId));
    }

    @PatchMapping("/increase/{itemId}/{productId}")
    public ResponseEntity<ItemDTO> increaseQuantity(
            @PathVariable Long itemsId,
            @PathVariable Long productId,
            @RequestBody @Valid UpdateItemQuantity increase
            ){
        return ResponseEntity.ok()
                .body(itemService.increaseQuantity(itemsId, productId, increase));
    }

    @PatchMapping("/decrease/{itemId}/{productId}")
    public ResponseEntity<ItemDTO> decreaseQuantity(
            @PathVariable Long itemsId,
            @PathVariable Long productId,
            @RequestBody @Valid UpdateItemQuantity decrease
    ){
        return ResponseEntity.ok()
                .body(itemService.decreaseQuantity(itemsId, productId, decrease));
    }

    @PatchMapping("/add/{itemId}/{orderId}")
    public ResponseEntity<Void> addToOrder(
            @PathVariable Long itemsId,
            @PathVariable Long orderId
    ){
        itemService.addToOrder(itemsId, orderId);
        return ResponseEntity.noContent().build();
    }
}
