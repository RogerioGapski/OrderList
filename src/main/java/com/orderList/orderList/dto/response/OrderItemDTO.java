package com.orderList.orderList.dto.response;

import jakarta.validation.constraints.NotNull;

public record OrderItemDTO(
            @NotNull Integer quantity,
            @NotNull Double unitary_price){
}

