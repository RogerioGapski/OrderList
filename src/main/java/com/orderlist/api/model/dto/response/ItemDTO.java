package com.orderlist.api.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemDTO(

        @Schema(description = "Item id", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
        Long id,

        @Schema(description = "Product quantity", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
        Integer quantity,

        @Schema(description = "Item price", example = "50.00", accessMode = Schema.AccessMode.READ_ONLY)
        BigDecimal unitaryPrice,

        @Schema(description = "Item product", accessMode = Schema.AccessMode.READ_ONLY)
        ProductDTO product,

        @Schema(description = "Item owner ID", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
        UUID userId
){
}

