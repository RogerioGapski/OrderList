package com.orderlist.api.model.dto.request.order;

import com.orderlist.api.model.enums.Payments;
import jakarta.validation.constraints.NotNull;

public record CreateOrderDTO(
        @NotNull Payments paymentType){
}


