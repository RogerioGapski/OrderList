package com.orderlist.api.controllers;

import com.orderlist.api.config.swagger.*;
import com.orderlist.api.model.dto.request.address.CreateAddressDTO;
import com.orderlist.api.model.dto.request.address.UpdateAddressDTO;
import com.orderlist.api.model.dto.response.AddressDTO;
import com.orderlist.api.security.authorization.CurrentUserId;
import com.orderlist.api.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "jwtAuth")
@RequestMapping("/addresses/{userId}")
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "Creates an address")
    @ApiProtectedCreateResponses
    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(
            @CurrentUserId UUID userId,
            @RequestBody @Valid CreateAddressDTO dto) {
        AddressDTO address = addressService.createAddress(dto, userId);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(address.id())
                .toUri();
        return ResponseEntity.created(uri).body(address);
    }

    @Operation(summary = "Delete an user address by ID")
    @ApiProtectedDeleteResponses
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable Long addressId) {
        addressService.deleteById(addressId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Finds an address by ID")
    @ApiProtectedReadResponses
    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDTO> findById(
            @PathVariable Long addressId) {
        return ResponseEntity.ok().body(addressService.findById(addressId));
    }

    @Operation(summary = "Finds all addresses for a user based on the user ID")
    @ApiProtectedReadResponses
    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAddresses(
            @PathVariable UUID userId) {
        return ResponseEntity.ok().body(addressService.getAddressesByUser(userId));
    }

    @Operation(summary = "Updates a user's address by address ID")
    @ApiProtectedUpdateResponses
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(
            @PathVariable Long addressId,
            @RequestBody @Valid UpdateAddressDTO dto) {
        return ResponseEntity.ok().body(addressService.updateAddress(dto, addressId));
    }
}
