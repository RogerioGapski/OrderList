package com.orderlist.api.model.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemDTO(
            Long id,
            Integer quantity,
            BigDecimal unitaryPrice,
            ProductDTO product,
            UUID userId){
}

