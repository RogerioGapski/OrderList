package com.orderlist.api.utils.mapper;

import com.orderlist.api.model.entities.Address;
import com.orderlist.api.model.dto.request.address.CreateAddressDTO;
import com.orderlist.api.model.dto.response.AddressDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDTO toDTO(Address address);
    Address toEntity(CreateAddressDTO dto);
}
