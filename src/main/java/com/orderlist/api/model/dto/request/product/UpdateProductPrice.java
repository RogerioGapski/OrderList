package com.orderlist.api.model.dto.request.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateProductPrice(
        @NotNull @Positive(message = "The price must be greater than zero.") BigDecimal price) {
}
