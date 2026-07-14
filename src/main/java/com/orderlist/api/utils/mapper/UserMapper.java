package com.orderlist.api.utils.mapper;

import com.orderlist.api.model.entities.User;
import com.orderlist.api.model.dto.request.auth.RegisterRequest;
import com.orderlist.api.model.dto.response.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", source = "user.id")
    UserDTO toDTO(User user);
    User toEntity(RegisterRequest dto);
}
