package com.orderList.orderList.services;

import com.orderList.orderList.domain.entities.Address;
import com.orderList.orderList.domain.entities.User;
import com.orderList.orderList.dto.request.CreateAddressDTO;
import com.orderList.orderList.dto.response.AddressDTO;
import com.orderList.orderList.mapper.AddressMapper;
import com.orderList.orderList.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressDTO createAddress(CreateAddressDTO dto, Integer userId) {
        Address address = addressMapper.toEntity(dto);
        addressRepository.save(address);
        return addressMapper.toDTO(address);
    }









}
