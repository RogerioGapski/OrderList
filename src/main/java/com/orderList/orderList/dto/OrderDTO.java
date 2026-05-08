package com.orderList.orderList.dto;

import com.orderList.orderList.enums.OrderStatus;
import com.orderList.orderList.enums.Payments;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderDTO {

    @NotNull
    private Date date;

    @NotNull
    private OrderStatus orderStatus;

    @NotNull
    private Double total;

    @NotNull
    private Payments paymentType;
}
