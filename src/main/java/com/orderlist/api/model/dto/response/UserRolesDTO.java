package com.orderlist.api.model.dto.response;

import java.util.Set;

public record UserRolesDTO(
        Set<RoleDTO> roles) {
}
