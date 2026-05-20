package com.orderList.orderList.model.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductDTO(
            @NotNull Long id,
            @NotBlank String name,
            @NotBlank String category,
            @NotNull @PositiveOrZero Double price,
            @NotNull @PositiveOrZero Integer stock){
}

