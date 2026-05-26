package com.orderList.orderList.model.dto.request.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateCategoryDTO(
        @NotBlank @Pattern(
                regexp = "^[a-zA-ZÀ-ú\\s]+$",
                message = "The category name must contain only letters.") String name) {
}
