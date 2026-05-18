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
import java.util.List;

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

    @DeleteMapping("/{userId}/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId
    ){
        addressService.deleteById(addressId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDTO> getById(
            @PathVariable Long addressId
    ){
        return ResponseEntity.ok().body(addressService.findById(addressId));
    }

    @GetMapping("/addresses/{userId}")
    public ResponseEntity<List<AddressDTO>> getAddressesByUser(
            @PathVariable Long userId
    ){
        return ResponseEntity.ok().body(addressService.getAddressesByUser(userId));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(
            @PathVariable Long addressId,
            @RequestBody @Valid CreateAddressDTO newAddress
    ){
        return ResponseEntity.ok().body(addressService.updateAddress(newAddress, addressId));
    }





}
