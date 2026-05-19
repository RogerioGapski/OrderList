package com.orderList.orderList.utils.mapper;

import com.orderList.orderList.model.entities.Address;
import com.orderList.orderList.model.dto.request.address.CreateAddressDTO;
import com.orderList.orderList.model.dto.response.AddressDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDTO toDTO(Address address);
    Address toEntity(CreateAddressDTO dto);
}
