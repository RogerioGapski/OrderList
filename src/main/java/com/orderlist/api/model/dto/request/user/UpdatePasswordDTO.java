package com.orderlist.api.model.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordDTO(
        @NotBlank @Size(min = 6, message = "The password must contain at least 6 characters.")
        String password) {
}
