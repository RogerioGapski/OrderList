package com.orderList.orderList.services;

import com.orderList.orderList.domain.entities.Address;
import com.orderList.orderList.domain.entities.User;
import com.orderList.orderList.dto.request.CreateAddressDTO;
import com.orderList.orderList.dto.response.AddressDTO;
import com.orderList.orderList.exceptions.NotFoundException;
import com.orderList.orderList.mapper.AddressMapper;
import com.orderList.orderList.repository.AddressRepository;
import com.orderList.orderList.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    public AddressDTO createAddress(CreateAddressDTO dto, Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Address address = addressMapper.toEntity(dto);
        address.setUsers(user);
        addressRepository.save(address);
        return addressMapper.toDTO(address);
    }

    public AddressDTO updateAddress(CreateAddressDTO addressUpdate, Integer id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address not found"));

        address.setCity(addressUpdate.city());
        address.setStreet(addressUpdate.street());
        address.setNumber(addressUpdate.number());

        addressRepository.save(address);
        return addressMapper.toDTO(address);
    }

    public void deleteAddress(Integer id) {
        Address deleteAddress = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address not found"));

        addressRepository.delete(deleteAddress);
    }

    public AddressDTO findAddressById(Integer id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address not found"));

        return addressMapper.toDTO(address);
    }
}
