package com.orderlist.api.model.dto.request.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateProductStock(
        @NotNull @PositiveOrZero(message = "The stock must be greater than zero.") Integer stock) {
}
