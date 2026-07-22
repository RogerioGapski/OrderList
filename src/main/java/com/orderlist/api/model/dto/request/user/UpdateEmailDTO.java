package com.orderlist.api.model.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateEmailDTO(

        @Schema(description = "New user email", example = "example@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Email
        String email
) {
}
