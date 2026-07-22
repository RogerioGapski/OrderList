package com.orderlist.api.model.dto.request.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateCategoryDTO(

        @Schema(description = "New category name", example = "Fruit", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Pattern(regexp = "^[a-zA-ZÀ-ú\\s]+$", message = "The category name must contain only letters.")
        String name
) {
}
