package com.orderlist.api.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public record UserRolesDTO(

        @Schema(description = "User data", accessMode = Schema.AccessMode.READ_ONLY)
        UserDTO user,

        @Schema(description = "User roles", accessMode = Schema.AccessMode.READ_ONLY)
        Set<RoleDTO> roles
) {
}
