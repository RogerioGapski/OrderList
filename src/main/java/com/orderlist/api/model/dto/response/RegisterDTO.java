package com.orderlist.api.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterDTO(

        @Schema(description = "Access token", accessMode = Schema.AccessMode.READ_ONLY)
        String accessToken,

        @Schema(description = "Token type", example = "Bearer", accessMode = Schema.AccessMode.READ_ONLY)
        String tokenType,

        @Schema(description = "Token validity period", accessMode = Schema.AccessMode.READ_ONLY)
        long expiresIn,

        @Schema(description = "User data", accessMode = Schema.AccessMode.READ_ONLY)
        UserDTO user
){
}

