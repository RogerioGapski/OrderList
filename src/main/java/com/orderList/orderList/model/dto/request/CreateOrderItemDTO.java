package com.orderList.orderList.model.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateOrderItemDTO(
        @NotNull Integer quantity){
}
