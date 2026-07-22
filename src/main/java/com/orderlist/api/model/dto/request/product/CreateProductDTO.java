package com.orderlist.api.model.dto.request.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateProductDTO(

        @Schema(description = "Name of the product to be created", example = "Pizza", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Pattern(regexp = "^[a-zA-ZÀ-ú\\s]+$", message = "The product name must contain only letters.")
        String name,

        @Schema(description = "ID of the category the product will belong to", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        Long categoryId,

        @Schema(description = "Price of product", example = "50.00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull @Positive(message = "The price must be greater than zero.")
        BigDecimal price,

        @Schema(description = "Stock of new product", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull @PositiveOrZero(message = "The stock must be zero or greater.")
        Integer stock
) {
}
