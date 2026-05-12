package com.orderList.orderList.controllers;

import com.orderList.orderList.domain.entities.Address;
import com.orderList.orderList.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    public ResponseEntity<List<Address>> getAddresses() {}





}
