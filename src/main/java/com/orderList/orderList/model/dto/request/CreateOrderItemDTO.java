package com.orderList.orderList.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateOrderItemDTO(
        @NotNull @PositiveOrZero(message = "The number must be zero or positive.") Integer quantity){
}
