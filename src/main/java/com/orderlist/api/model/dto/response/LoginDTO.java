package com.orderlist.api.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record LoginDTO(

        @Schema(description = "User ID", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
        UUID userId,

        @Schema(description = "Access token", accessMode = Schema.AccessMode.READ_ONLY)
        String accessToken,

        @Schema(description = "Token type", example = "Bearer", accessMode = Schema.AccessMode.READ_ONLY)
        String tokenType,

        @Schema(description = "Token validity period", accessMode = Schema.AccessMode.READ_ONLY)
        long expiresIn
){
}
