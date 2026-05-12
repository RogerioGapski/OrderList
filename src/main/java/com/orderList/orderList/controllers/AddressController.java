package com.orderList.orderList.controllers;

import com.orderList.orderList.dto.request.CreateAddressDTO;
import com.orderList.orderList.dto.response.AddressDTO;
import com.orderList.orderList.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/{id}")
    public ResponseEntity<AddressDTO> createAddress(
            @RequestBody CreateAddressDTO dto,
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(addressService.createAddress(dto, id));
    }






}
