package com.orderList.orderList.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(
    @NotBlank String name,
    @NotBlank String email) {
}

