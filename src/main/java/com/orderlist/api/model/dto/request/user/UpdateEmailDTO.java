package com.orderlist.api.model.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateEmailDTO(
        @NotBlank @Email String email) {
}
