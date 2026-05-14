package com.orderList.orderList.utils.mapper;

import com.orderList.orderList.model.entities.User;
import com.orderList.orderList.model.dto.request.CreateUserDTO;
import com.orderList.orderList.model.dto.response.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(CreateUserDTO dto);
}
