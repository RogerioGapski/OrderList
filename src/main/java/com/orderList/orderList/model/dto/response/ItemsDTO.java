package com.orderList.orderList.model.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ItemsDTO(
            @NotNull @PositiveOrZero Integer quantity,
            @NotNull @PositiveOrZero Double unitaryPrice){
}

