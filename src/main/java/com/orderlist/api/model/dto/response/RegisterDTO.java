package com.orderlist.api.model.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDTO{
        String accessToken;
        String tokenType;
        long expiresIn;
}
