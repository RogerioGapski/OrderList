package com.orderList.orderList.controllers;

import com.orderList.orderList.model.dto.request.address.CreateAddressDTO;
import com.orderList.orderList.model.dto.request.address.UpdateAddressDTO;
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
            @PathVariable Long userId,
            @RequestBody @Valid CreateAddressDTO dto
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
        addressService.deleteById(userId, addressId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/{addressId}")
    public ResponseEntity<AddressDTO> findById(
            @PathVariable Long userId,
            @PathVariable Long addressId
    ){
        return ResponseEntity.ok().body(addressService.findById(userId, addressId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressDTO>> findAddresses(
            @PathVariable Long userId
    ){
        return ResponseEntity.ok().body(addressService.getAddressesByUser(userId));
    }

    @PutMapping("/{userId}/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @RequestBody @Valid UpdateAddressDTO newAddress
    ){
        return ResponseEntity.ok().body(addressService.updateAddress(userId, newAddress, addressId));
    }
}
