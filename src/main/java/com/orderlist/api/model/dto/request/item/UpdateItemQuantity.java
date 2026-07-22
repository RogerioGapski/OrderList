package com.orderlist.api.model.dto.request.item;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateItemQuantity(

        @Schema(description = "New product quantity", example = "10", minimum = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull @Positive(message = "The quantity must be greater than zero.")
        Integer quantity
){
}
