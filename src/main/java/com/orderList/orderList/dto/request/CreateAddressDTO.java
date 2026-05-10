package com.orderList.orderList.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAddressDTO(
        @NotBlank String city,
        @NotBlank String street,
        @NotNull Integer number){
}

