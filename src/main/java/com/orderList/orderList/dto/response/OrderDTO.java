package com.orderList.orderList.dto.response;

import com.orderList.orderList.domain.enums.OrderStatus;
import com.orderList.orderList.domain.enums.Payments;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderDTO(
            @NotNull OrderStatus orderStatus,
            @NotNull Double total,
            @NotBlank Payments paymentType){
}

