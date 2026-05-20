package com.orderList.orderList.model.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateProductCategory(
        @NotBlank @Pattern(
                regexp = "^[a-zA-ZÀ-ú\\s]+$") String category
) {
}
