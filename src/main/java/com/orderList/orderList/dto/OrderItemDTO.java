package com.orderList.orderList.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderItemDTO {

    @NotNull
    private Integer quantity;

    @NotNull
    private Double unitary_price;
}
