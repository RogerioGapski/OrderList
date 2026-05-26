package com.orderList.orderList.model.dto.request.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateItemQuantity(
        @NotNull @Positive(message = "The quantity must be greater than zero.") Integer quantity){
}
