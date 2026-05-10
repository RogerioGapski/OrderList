package com.orderList.orderList.mapper;

import com.orderList.orderList.domain.entities.User;
import com.orderList.orderList.dto.request.CreateUserDTO;
import com.orderList.orderList.dto.response.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(CreateUserDTO dto);
}
