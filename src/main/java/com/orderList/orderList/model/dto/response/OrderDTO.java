package com.orderList.orderList.model.dto.response;

import com.orderList.orderList.model.enums.OrderStatus;
import com.orderList.orderList.model.enums.Payments;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record OrderDTO(
        @NotNull Long id,
        @NotNull OrderStatus orderStatus,
        @NotNull @PositiveOrZero Double total,
        @NotBlank Payments paymentType,
        @NotNull List<ItemsDTO> items){
}

