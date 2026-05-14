package com.orderList.orderList.model.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record OrderItemDTO(
            @NotNull Long id,
            @NotNull @PositiveOrZero Integer quantity,
            @NotNull @PositiveOrZero Double unitary_price){
}

