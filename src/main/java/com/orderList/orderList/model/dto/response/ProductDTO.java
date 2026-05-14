package com.orderList.orderList.model.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(
            @NotNull Long id,
            @NotBlank String name,
            @NotNull Double price,
            @NotNull Integer stock){
}

