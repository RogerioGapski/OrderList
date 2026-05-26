package com.orderList.orderList.model.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateProductName(
        @NotBlank @Pattern(
                regexp = "^[a-zA-ZÀ-ú\\s]+$",
                message = "The name must contain only letters.") String name) {
}
