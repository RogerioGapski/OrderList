package com.orderList.orderList.model.dto.request.order;

import com.orderList.orderList.model.enums.Payments;
import jakarta.validation.constraints.NotBlank;

public record CreateOrderDTO(
        @NotBlank Payments paymentType){
}


