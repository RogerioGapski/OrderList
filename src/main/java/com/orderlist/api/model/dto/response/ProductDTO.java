package com.orderlist.api.model.dto.response;

public record ProductDTO(
            Long id,
            String name,
            CategoryDTO category,
            Double price,
            Integer stock){
}

