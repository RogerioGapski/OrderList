package com.orderList.orderList.model.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateProductDTO(
        @NotBlank @Pattern(
                regexp = "^[a-zA-Z]+$") String name,
        @NotNull Double price,
        @NotNull Integer stock) {
}
