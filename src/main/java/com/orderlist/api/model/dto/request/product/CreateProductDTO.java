package com.orderlist.api.model.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateProductDTO(
        @NotBlank @Pattern(
                regexp = "^[a-zA-ZÀ-ú\\s]+$",
                message = "The product name must contain only letters.") String name,

        @NotNull Long categoryId,
        @NotNull @Positive(message = "The price must be greater than zero.") Double price,
        @NotNull @PositiveOrZero(message = "The stock must be zero or greater.") Integer stock) {
}
