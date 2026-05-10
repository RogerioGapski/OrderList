package com.orderList.orderList.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDTO(
            @NotBlank String city,
            @NotBlank String street,
            @NotNull Integer number){
}

