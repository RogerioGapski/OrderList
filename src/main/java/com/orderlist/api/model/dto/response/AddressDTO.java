package com.orderlist.api.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record AddressDTO(

        @Schema(description = "Address owner ID", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
        UUID userId,

        @Schema(description = "Address ID", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
        Long id,

        @Schema(description = "City name from the address", example = "New York", accessMode = Schema.AccessMode.READ_ONLY)
        String city,

        @Schema(description = "Street name from the address", example = "Wall Street", accessMode = Schema.AccessMode.READ_ONLY)
        String street,

        @Schema(description = "House number in the address", example = "2091", accessMode = Schema.AccessMode.READ_ONLY)
        String number
){
}

