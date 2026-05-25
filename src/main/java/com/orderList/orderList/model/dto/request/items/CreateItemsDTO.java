package com.orderList.orderList.model.dto.request.items;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateItemsDTO(
        @NotNull @Positive(message = "The number must be bigger than zero.") Integer quantity){
}
