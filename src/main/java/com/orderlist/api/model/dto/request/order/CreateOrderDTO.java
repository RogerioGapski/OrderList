package com.orderlist.api.model.dto.request.order;

import com.orderlist.api.model.enums.Payments;
import jakarta.validation.constraints.NotBlank;

public record CreateOrderDTO(
        @NotBlank Payments paymentType){
}


