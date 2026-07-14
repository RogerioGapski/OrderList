package com.orderlist.api.model.dto.response;

import java.util.UUID;

public record LoginDTO(
        UUID userId,
        String accessToken,
        String tokenType,
        long expiresIn){
}
