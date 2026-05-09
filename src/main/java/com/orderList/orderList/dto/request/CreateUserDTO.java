package com.orderList.orderList.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateUserDTO {
    public record userDTO(
            @NotBlank String name,
            @NotBlank String email) {
    }
}
