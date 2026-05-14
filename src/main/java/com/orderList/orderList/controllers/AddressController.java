package com.orderList.orderList.controllers;

import com.orderList.orderList.model.dto.request.CreateAddressDTO;
import com.orderList.orderList.model.dto.response.AddressDTO;
import com.orderList.orderList.services.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/{userId}")
    public ResponseEntity<AddressDTO> createAddress(
            @RequestBody @Valid CreateAddressDTO dto,
            @PathVariable Long userId
    ){
        AddressDTO address = addressService.createAddress(dto, userId);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(address.id())
                .toUri();

        return ResponseEntity.created(uri).body(address);
    }






}
