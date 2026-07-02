package com.orderlist.api.model.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDTO{
        String accessToken;
        String tokenType;
        long expiresIn;
}
