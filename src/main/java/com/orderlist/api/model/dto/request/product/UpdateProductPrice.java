package com.orderlist.api.model.dto.request.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateProductPrice(

        @Schema(description = "New product price", example = "50.00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull @Positive(message = "The price must be greater than zero.")
        BigDecimal price
) {
}
