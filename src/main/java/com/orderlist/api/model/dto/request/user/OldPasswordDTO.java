package com.orderlist.api.model.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OldPasswordDTO(
        @Schema(description = "Old user password", example = "MyPassword123", format = "password", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Size(min = 6, message = "The password must contain at least 6 characters.")
        String password
) {
}
