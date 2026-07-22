package com.orderlist.api.model.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateNameDTO(

        @Schema(description = "New user name", example = "Cristiano Ronaldo", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Pattern(regexp = "^[a-zA-ZÀ-ú\\s]+$", message = "The user name must contain only letters.")
        String name
) {
}
