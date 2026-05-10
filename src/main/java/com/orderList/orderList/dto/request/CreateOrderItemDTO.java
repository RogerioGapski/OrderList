package com.orderList.orderList.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateOrderItemDTO(
        @NotNull Integer quantity){
}
