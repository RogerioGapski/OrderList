package com.orderList.orderList.services;

import com.orderList.orderList.exceptions.customs.UnauthorizedException;
import com.orderList.orderList.model.dto.request.address.UpdateAddressDTO;
import com.orderList.orderList.model.entities.Address;
import com.orderList.orderList.model.entities.User;
import com.orderList.orderList.model.dto.request.address.CreateAddressDTO;
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
    public AddressDTO createAddress(CreateAddressDTO dto, Long userId) {
        User user = findUserById(userId);
        Address address = addressMapper.toEntity(dto);
        address.setUser(user);
        addressRepository.save(address);
        return addressMapper.toDTO(address);
    }

    @Transactional
    public void deleteById(Long userId, Long addressId) {
        User user = findUserById(userId);
        Address address = findAddressById(addressId);
        checkOwnership(address, user);
        addressRepository.deleteById(addressId);
    }

    public AddressDTO findById(Long userId, Long addressId) {
        User user = findUserById(userId);
        Address address = findAddressById(addressId);
        checkOwnership(address, user);
        return addressMapper.toDTO(address);
    }

    public List<AddressDTO> getAddressesByUser(Long userId) {
        User user = findUserById(userId);
        return user.getAddresses().stream()
                .map(addressMapper::toDTO)
                .toList();
    }

    @Transactional
    public AddressDTO updateAddress(Long userId, UpdateAddressDTO dto, Long addressId) {
        User user = findUserById(userId);
        Address address = findAddressById(addressId);
        checkOwnership(address, user);

        address.setCity(dto.city());
        address.setStreet(dto.street());
        address.setNumber(dto.number());

        addressRepository.save(address);
        return addressMapper.toDTO(address);
    }

    private User findUserById(Long userId){
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
