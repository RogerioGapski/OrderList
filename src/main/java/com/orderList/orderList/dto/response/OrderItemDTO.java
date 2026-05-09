package com.orderList.orderList.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@Builder
@ToString
public class OrderItemDTO {
    public record orderItemDTO(
            @NotNull Integer quantity,
            @NotNull Double unitary_price){
    }
}
