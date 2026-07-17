package com.orderlist.api.controllers;

import com.orderlist.api.model.dto.request.auth.LoginRequest;
import com.orderlist.api.model.dto.request.auth.RegisterRequest;
import com.orderlist.api.model.dto.response.LoginDTO;
import com.orderlist.api.model.dto.response.RegisterDTO;
import com.orderlist.api.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterDTO> createUser(@RequestBody @Valid RegisterRequest dto) {
        RegisterDTO register = authService.registerUser(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(register.user().id())
                .toUri();
        return ResponseEntity.created(uri).body(register);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody @Valid LoginRequest dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}
