package com.orderList.orderList.services;

import com.orderList.orderList.model.entities.Address;
import com.orderList.orderList.model.entities.User;
import com.orderList.orderList.model.dto.request.CreateAddressDTO;
import com.orderList.orderList.model.dto.response.AddressDTO;
import com.orderList.orderList.exceptions.customs.NotFoundException;
import com.orderList.orderList.utils.mapper.AddressMapper;
import com.orderList.orderList.repository.AddressRepository;
import com.orderList.orderList.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    @Transactional
    public AddressDTO createAddress(CreateAddressDTO dto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Address address = addressMapper.toEntity(dto);
        address.setUser(user);
        addressRepository.save(address);
        return addressMapper.toDTO(address);
    }

    @Transactional
    public void deleteById(Long id) {
        Address deleteAddress = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address not found"));

        addressRepository.delete(deleteAddress);
    }

    public AddressDTO findById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address not found"));

        return addressMapper.toDTO(address);
    }

    public List<AddressDTO> getAddressesById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address not found"));

        return user.getAddresses().stream()
                .map(addressMapper::toDTO)
                .toList();
    }

    @Transactional
    public AddressDTO updateAddress(CreateAddressDTO addressUpdate, Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address not found"));

        address.setCity(addressUpdate.city());
        address.setStreet(addressUpdate.street());
        address.setNumber(addressUpdate.number());

        addressRepository.save(address);
        return addressMapper.toDTO(address);
    }
}
