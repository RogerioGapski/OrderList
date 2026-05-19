package com.orderList.orderList.model.dto.request.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateProductStock(
        @NotNull @PositiveOrZero Integer stock) {
}
