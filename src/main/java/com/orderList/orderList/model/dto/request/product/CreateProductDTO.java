package com.orderList.orderList.model.dto.request.product;

import com.orderList.orderList.model.entities.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateProductDTO(
        @NotBlank @Pattern(
                regexp = "^[a-zA-ZÀ-ú\\s]+$") String name,

        @NotBlank Category category,
        @NotNull Double price,
        @NotNull Integer stock) {
}
