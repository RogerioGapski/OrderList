package com.orderList.orderList.model.dto.request.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateAddressDTO(
        @NotBlank @Pattern(
                regexp = "^[a-zA-Z]+$", message = "The city name must contain only letters"
        ) String city,

        @NotBlank @Pattern(
                regexp = "^[a-zA-Z]+$", message = "The street name must contain only letters"
        ) String street,

        @NotBlank @Pattern(
                regexp = "^[0-9]+$", message = "The house number must contain only numbers."
        ) String number){
}

