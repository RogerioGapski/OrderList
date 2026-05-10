package com.orderList.orderList.dto.request;

import com.orderList.orderList.domain.enums.Payments;
import jakarta.validation.constraints.NotBlank;

public record CreateOrderDTO(
        @NotBlank Payments paymentType){
}


