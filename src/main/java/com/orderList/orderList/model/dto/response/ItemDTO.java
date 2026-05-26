package com.orderList.orderList.model.dto.response;

public record ItemDTO(
            Long id,
            Integer quantity,
            Double unitaryPrice,
            ProductDTO product){
}

