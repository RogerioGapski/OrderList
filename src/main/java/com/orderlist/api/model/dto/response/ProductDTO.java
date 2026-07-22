package com.orderlist.api.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ProductDTO(

        @Schema(description = "Product ID", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
        Long id,

        @Schema(description = "Product name", example = "Apple", accessMode = Schema.AccessMode.READ_ONLY)
        String name,

        @Schema(description = "Product category", example = "Fruit", accessMode = Schema.AccessMode.READ_ONLY)
        CategoryDTO category,

        @Schema(description = "Product price", example = "1.50", accessMode = Schema.AccessMode.READ_ONLY)
        BigDecimal price,

        @Schema(description = "Product stock", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
        Integer stock
){
}

