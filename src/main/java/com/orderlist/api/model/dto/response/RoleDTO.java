package com.orderlist.api.model.dto.response;

import com.orderlist.api.model.enums.Roles;
import io.swagger.v3.oas.annotations.media.Schema;

public record RoleDTO(

        @Schema(description = "Role ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
        Long id,

        @Schema(description = "Role name", example = "Admin", accessMode = Schema.AccessMode.READ_ONLY)
        Roles name
) {
}
