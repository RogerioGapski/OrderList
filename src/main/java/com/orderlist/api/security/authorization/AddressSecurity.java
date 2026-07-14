package com.orderlist.api.security.authorization;

import com.orderlist.api.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("addressSecurity")
@RequiredArgsConstructor
public class AddressSecurity {

    private final AddressRepository addressRepository;

    public boolean isOwner(Long addressId, UUID userId){
        return addressRepository.findById(addressId)
                .map(address -> address.getUser().getId().equals(userId))
                .orElse(false);
    }
}
