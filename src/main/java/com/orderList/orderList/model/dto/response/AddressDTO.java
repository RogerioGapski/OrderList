package com.orderList.orderList.model.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AddressDTO(
            @NotNull Long id,
            @NotBlank String city,
            @NotBlank String street,
            @NotBlank String number){
}

