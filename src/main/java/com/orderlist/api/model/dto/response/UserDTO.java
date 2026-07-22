package com.orderlist.api.model.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record UserDTO(

        @Schema(description = "User ID", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
        UUID id,

        @Schema(description = "User name", example = "Jude Bellingham", accessMode = Schema.AccessMode.READ_ONLY)
        String name,

        @Schema(description = "User email", example = "example@gmail.com", accessMode = Schema.AccessMode.READ_ONLY)
        String email
){
}

