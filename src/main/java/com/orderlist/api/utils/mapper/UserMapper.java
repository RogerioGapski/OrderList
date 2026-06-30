package com.orderlist.api.utils.mapper;

import com.orderlist.api.model.entities.User;
import com.orderlist.api.model.dto.request.auth.RegisterRequest;
import com.orderlist.api.model.dto.response.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(RegisterRequest dto);
}
