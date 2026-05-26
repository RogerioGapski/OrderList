package com.orderList.orderList.model.dto.request.items;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateItemDTO(
        @NotNull @Positive(message = "The quantity must be greater than zero.") Integer quantity){
}
