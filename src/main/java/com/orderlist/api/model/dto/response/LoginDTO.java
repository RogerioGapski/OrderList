package com.orderlist.api.model.dto.response;

public record LoginDTO(
        String accessToken,
        Long expiresIn){
}
