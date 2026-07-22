package com.orderlist.api.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryDTO(

        @Schema(description = "Category ID", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
        Long id,

        @Schema(description = "Category name", example = "Vegetables", accessMode = Schema.AccessMode.READ_ONLY)
        String name
){
}
