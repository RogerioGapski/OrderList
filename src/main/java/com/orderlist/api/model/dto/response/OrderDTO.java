package com.orderlist.api.model.dto.response;

import com.orderlist.api.model.enums.OrderStatus;
import com.orderlist.api.model.enums.Payments;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderDTO(

        @Schema(description = "Order ID", example = "10", accessMode = Schema.AccessMode.READ_ONLY)
        Long id,

        @Schema(description = "Order creation date", accessMode = Schema.AccessMode.READ_ONLY)
        Instant date,

        @Schema(description = "Order status", example = "Pending", accessMode = Schema.AccessMode.READ_ONLY)
        OrderStatus status,

        @Schema(description = "Order total price", example = "50.00", accessMode = Schema.AccessMode.READ_ONLY)
        BigDecimal total,

        @Schema(description = "Payment type", example = "Pix", accessMode = Schema.AccessMode.READ_ONLY)
        Payments paymentType,

        @Schema(description = "Order item list", accessMode = Schema.AccessMode.READ_ONLY)
        List<ItemDTO> items
){
}

