package com.orderlist.api.model.dto.response;

import java.math.BigDecimal;

public record ItemDTO(
            Long id,
            Integer quantity,
            BigDecimal unitaryPrice,
            ProductDTO product){
}

