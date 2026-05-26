package com.orderList.orderList.model.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateNameDTO(
        @NotBlank @Pattern(
                regexp = "^[a-zA-ZÀ-ú\\s]+$",
                message = "The user name must contain only letters.") String name) {
}
