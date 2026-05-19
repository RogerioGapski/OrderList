package com.orderList.orderList.model.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AddressDTO(
            @NotBlank String city,
            @NotBlank String street,
            @NotBlank @Pattern(
                    regexp = "^[0-9]+$", message = "The house number should contain only numbers."
            ) String number){
}

