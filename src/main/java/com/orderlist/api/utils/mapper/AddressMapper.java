package com.orderlist.api.utils.mapper;

import com.orderlist.api.model.entities.Address;
import com.orderlist.api.model.dto.request.address.CreateAddressDTO;
import com.orderlist.api.model.dto.response.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "userId", source = "user.id")
    AddressDTO toDTO(Address address);
    Address toEntity(CreateAddressDTO dto);
}
