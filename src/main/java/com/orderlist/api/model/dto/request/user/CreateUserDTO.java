package com.orderlist.api.model.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateUserDTO(
        @NotBlank String password,
        @Email @NotBlank String email,
        @NotBlank @Pattern(
           regexp = "^[a-zA-ZÀ-ú\\s]+$",
           message = "The user name must contain only letters.") String name) {
}

