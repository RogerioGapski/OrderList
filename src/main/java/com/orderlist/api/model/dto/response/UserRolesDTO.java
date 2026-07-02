package com.orderlist.api.model.dto.response;

import com.orderlist.api.model.entities.Role;

import java.util.Set;

public record UserRolesDTO(
        Set<Role> roles) {
}
