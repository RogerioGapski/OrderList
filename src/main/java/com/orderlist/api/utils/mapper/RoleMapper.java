package com.orderlist.api.utils.mapper;

import com.orderlist.api.model.dto.response.RoleDTO;
import com.orderlist.api.model.entities.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
        RoleDTO toDTO(Role role);
}
