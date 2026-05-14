package com.orderList.orderList.model.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDTO(
            @NotNull Long id,
            @NotBlank String city,
            @NotBlank String street,
            @NotNull Integer number){
}

