package com.orderlist.api.model.dto.request.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateAddressDTO(
        @NotBlank @Pattern(
                regexp = "^[a-zA-ZÀ-ú\\s]+$",
                message = "The city name must contain only letters") String city,

        @NotBlank @Pattern(
                regexp = "^[a-zA-ZÀ-ú\\s]+$",
                message = "The street name must contain only letters") String street,

        @NotBlank @Pattern(
                regexp = "^[0-9]+$",
                message = "The house number must contain only digits.") String number) {
}


