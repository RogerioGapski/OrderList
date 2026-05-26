package com.orderList.orderList.model.dto.response;

import com.orderList.orderList.model.enums.OrderStatus;
import com.orderList.orderList.model.enums.Payments;

import java.time.Instant;
import java.util.List;

public record OrderDTO(
        Long id,
        Instant date,
        OrderStatus status,
        Double total,
        Payments paymentType,
        List<ItemDTO> items){
}

