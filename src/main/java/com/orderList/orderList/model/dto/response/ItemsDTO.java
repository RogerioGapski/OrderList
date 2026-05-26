package com.orderList.orderList.model.dto.response;

public record ItemsDTO(
            Long id,
            Integer quantity,
            Double unitaryPrice,
            ProductDTO product){
}

