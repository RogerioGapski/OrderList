package com.orderlist.api.model.dto.request.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateProductPrice(
        @NotNull @PositiveOrZero(message = "The price must be greater than zero.") Double price) {
}
