package com.orderlist.api.model.dto.response;

public record LoginDTO(
        String accessToken,
        String tokenType,
        long expiresIn){
}
