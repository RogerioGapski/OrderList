package com.orderlist.api.model.dto.response;

import com.orderlist.api.model.enums.OrderStatus;
import com.orderlist.api.model.enums.Payments;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderDTO(
        Long id,
        Instant date,
        OrderStatus status,
        BigDecimal total,
        Payments paymentType,
        List<ItemDTO> items){
}

