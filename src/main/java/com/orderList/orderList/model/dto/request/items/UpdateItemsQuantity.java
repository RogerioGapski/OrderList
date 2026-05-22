package com.orderList.orderList.model.dto.request.items;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record UpdateItemsQuantity(
        @NotNull @Positive @Pattern(
                regexp = "^[1-9]+$", message = "The number must be bigger than zero."
        ) Integer quantity
){
}
