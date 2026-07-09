package com.orderlist.api.model.dto.response;

import java.math.BigDecimal;

public record ProductDTO(
            Long id,
            String name,
            CategoryDTO category,
            BigDecimal price,
            Integer stock){
}

