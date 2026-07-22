package com.orderlist.api.model.dto.request.order;

import com.orderlist.api.model.enums.Payments;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateOrderDTO(

        @Schema(description = "Payment type", example = "Pix", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull Payments paymentType
){
}


