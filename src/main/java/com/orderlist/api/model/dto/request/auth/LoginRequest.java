package com.orderlist.api.model.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(

        @Schema(description = "User's email", example = "example@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Email
        String email,

        @Schema(description = "User's password", example = "MyPassword123", format = "password", requiredMode = Schema.RequiredMode.REQUIRED, accessMode = Schema.AccessMode.WRITE_ONLY)
        @NotBlank @Size(min = 8, message = "The password must contain at least 8 characters.")
        String password
) {
}
