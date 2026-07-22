package com.orderlist.api.model.dto.request.address;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateAddressDTO(

        @Schema(description = "User's new city name", example = "New York", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Pattern(regexp = "^[a-zA-ZÀ-ú\\s]+$", message = "The city name must contain only letters")
        String city,

        @Schema(description = "User's new street name", example = "Wall Street", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Pattern(regexp = "^[a-zA-Z]+$", message = "The street name must contain only letters")
        String street,

        @Schema(description = "User's new house number", example = "2091", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Pattern(regexp = "^[0-9]+$", message = "The house number must contain only numbers.")
        String number
) {
}

