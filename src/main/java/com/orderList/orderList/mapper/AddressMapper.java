package com.orderList.orderList.mapper;

import com.orderList.orderList.domain.entities.Address;
import com.orderList.orderList.dto.request.CreateAddressDTO;
import com.orderList.orderList.dto.response.AddressDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {
    AddressDTO toDTO(Address address);
    Address toEntity(CreateAddressDTO address);
}
