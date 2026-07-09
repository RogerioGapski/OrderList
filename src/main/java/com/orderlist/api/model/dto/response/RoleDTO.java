package com.orderlist.api.model.dto.response;

import com.orderlist.api.model.enums.Roles;

public record RoleDTO(
        Long id,
        Roles name) {
}
