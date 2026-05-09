package com.orderList.orderList.dto.request;

import jakarta.validation.constraints.NotNull;

public class CreateOrderItemDTO {
    public record orderItemDTO(
            @NotNull Integer quantity){
    }
}
