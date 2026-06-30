package com.orderlist.api.controllers;

import com.orderlist.api.model.dto.request.auth.LoginRequest;
import com.orderlist.api.model.dto.response.LoginDTO;
import com.orderlist.api.model.dto.response.UserDTO;
import com.orderlist.api.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenController {

    private final UserService userService;
    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder passwordEncode;
}
