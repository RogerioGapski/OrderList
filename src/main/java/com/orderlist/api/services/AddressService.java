package com.orderlist.api.services;

import com.orderlist.api.exceptions.customs.UnauthorizedException;
import com.orderlist.api.model.dto.request.address.UpdateAddressDTO;
import com.orderlist.api.model.entities.Address;
import com.orderlist.api.model.entities.User;
import com.orderlist.api.model.dto.request.address.CreateAddressDTO;
import com.orderlist.api.model.dto.response.AddressDTO;
import com.orderlist.api.exceptions.customs.NotFoundException;
import com.orderlist.api.utils.mapper.AddressMapper;
import com.orderlist.api.repository.AddressRepository;
import com.orderlist.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    @Transactional
    public AddressDTO createAddress(CreateAddressDTO dto, UUID userId) {
        User user = findUserById(userId);
        Address address = addressMapper.toEntity(dto);
        address.setUser(user);
        addressRepository.save(address);
        return addressMapper.toDTO(address);
    }

    @Transactional
    public void deleteById(UUID userId, Long addressId) {
        User user = findUserById(userId);
        Address address = findAddressById(addressId);
        checkOwnership(address, user);
        addressRepository.deleteById(addressId);
    }

    public AddressDTO findById(UUID userId, Long addressId) {
        User user = findUserById(userId);
        Address address = findAddressById(addressId);
        checkOwnership(address, user);
        return addressMapper.toDTO(address);
    }

    public List<AddressDTO> getAddressesByUser(UUID userId) {
        User user = findUserById(userId);
        return user.getAddresses().stream()
                .map(addressMapper::toDTO)
                .toList();
    }

    @Transactional
    public AddressDTO updateAddress(UUID userId, UpdateAddressDTO dto, Long addressId) {
        User user = findUserById(userId);
        Address address = findAddressById(addressId);
        checkOwnership(address, user);

        address.setCity(dto.city());
        address.setStreet(dto.street());
        address.setNumber(dto.number());

        addressRepository.save(address);
        return addressMapper.toDTO(address);
    }

    //Auxiliary methods
    private User findUserById(UUID userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private Address findAddressById(Long addressId){
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException("Address not found"));
    }

    private void checkOwnership(Address address, User user) {
        if(!address.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("This address does not belong to the user.");
        }
    }
}
