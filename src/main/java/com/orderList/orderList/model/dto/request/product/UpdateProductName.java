package com.orderList.orderList.model.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateProductName(
        @NotBlank @Pattern(
                regexp = "^[a-zA-Z]+$0") String name) {
}
