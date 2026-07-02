package com.orderlist.api.model.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Email @NotBlank String email,
        @NotBlank @Size(
                min = 8, message = "The password must contain at least 8 characters.") String password,
        @NotBlank @Pattern(
           regexp = "^[a-zA-ZÀ-ú\\s]+$",
           message = "The user name must contain only letters.") String name) {
}

