package com.orderList.orderList.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChangeUserEmailDTO(
        @NotBlank @Email String email){
}
