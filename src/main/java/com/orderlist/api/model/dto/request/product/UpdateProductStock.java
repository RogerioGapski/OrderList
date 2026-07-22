package com.orderlist.api.model.dto.request.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateProductStock(

        @Schema(description = "New product stock", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull @PositiveOrZero
        Integer stock) {
}
