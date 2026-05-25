package com.orderList.orderList.controllers;

import com.orderList.orderList.model.dto.request.items.CreateItemsDTO;
import com.orderList.orderList.model.dto.request.items.UpdateItemsQuantity;
import com.orderList.orderList.model.dto.response.ItemsDTO;
import com.orderList.orderList.services.ItemsService;
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
public class ItemsController {

    private final ItemsService itemsService;

    @PostMapping("/{productId}")
    public ResponseEntity<ItemsDTO> createItems(
            @PathVariable Long productId,
            @RequestBody @Valid CreateItemsDTO dto
    ){
        ItemsDTO items = itemsService.createItems(dto, productId);
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
        itemsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemsDTO> getById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok().body(itemsService.findById(id));
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<ItemsDTO>> getAll(
            @PathVariable Long userId
    ){
        return ResponseEntity.ok().body(itemsService.findAll(userId));
    }

    @PatchMapping("/increase/{itemId}/{productId}")
    public ResponseEntity<ItemsDTO> increaseQuantity(
            @PathVariable Long itemsId,
            @PathVariable Long productId,
            @RequestBody @Valid UpdateItemsQuantity increase
            ){
        return ResponseEntity.ok()
                .body(itemsService.increaseQuantity(itemsId, productId, increase));
    }

    @PatchMapping("/decrease/{itemId}/{productId}")
    public ResponseEntity<ItemsDTO> decreaseQuantity(
            @PathVariable Long itemsId,
            @PathVariable Long productId,
            @RequestBody @Valid UpdateItemsQuantity decrease
    ){
        return ResponseEntity.ok()
                .body(itemsService.decreaseQuantity(itemsId, productId, decrease));
    }

    @PatchMapping("/add/{itemId}/{orderId}")
    public ResponseEntity<Void> addToOrder(
            @PathVariable Long itemsId,
            @PathVariable Long orderId
    ){
        itemsService.addToOrder(itemsId, orderId);
        return ResponseEntity.noContent().build();
    }
}
