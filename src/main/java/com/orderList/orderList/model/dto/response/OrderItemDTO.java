package com.orderList.orderList.model.dto.response;

import jakarta.validation.constraints.NotNull;

public record OrderItemDTO(
            @NotNull Long id,
            @NotNull Integer quantity,
            @NotNull Double unitary_price){
}

