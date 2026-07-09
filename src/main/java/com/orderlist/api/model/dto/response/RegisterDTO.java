package com.orderlist.api.model.dto.response;

public record RegisterDTO(
        String accessToken,
        String tokenType,
        long expiresIn,
        UserDTO user){
}

