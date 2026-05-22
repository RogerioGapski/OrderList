package com.orderList.orderList.controllers;

import com.orderList.orderList.model.dto.request.items.CreateItemsDTO;
import com.orderList.orderList.model.dto.request.items.UpdateItemsQuantity;
import com.orderList.orderList.model.dto.response.ItemsDTO;
import com.orderList.orderList.model.entities.Items;
import com.orderList.orderList.services.ItemsService;
import com.orderList.orderList.utils.mapper.ItemsMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemsController {

    private final ItemsService itemsService;
    private final ItemsMapper itemsMapper;

    @PostMapping
    public ResponseEntity<ItemsDTO> createItems(
            @RequestBody @Valid CreateItemsDTO dto
    ){
        ItemsDTO items = itemsService.createItems(dto);
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

    @PatchMapping("/increase/{itemId}/{productId}")
    public ResponseEntity<ItemsDTO> increaseQuantity(
            @PathVariable Long itemsId,
            @PathVariable Long productId,
            @RequestBody @Valid UpdateItemsQuantity quantity
            ){
        return ResponseEntity.ok()
                .body(itemsService.increaseQuantity(itemsId, productId, quantity.quantity()));
    }

    @PatchMapping("/decrease/{itemId}/{productId}")
    public ResponseEntity<ItemsDTO> decreaseQuantity(
            @PathVariable Long itemsId,
            @PathVariable Long productId,
            @RequestBody @Valid UpdateItemsQuantity quantity
    ){
        return ResponseEntity.ok()
                .body(itemsService.decreaseQuantity(itemsId, productId, quantity));
    }


    
}
