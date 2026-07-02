package com.orderlist.api.model.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDTO{
        String accessToken;
        String tokenType;
        long expiresIn;
}
