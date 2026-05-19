package com.orderList.orderList.model.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(
          @NotNull Long id,
          @NotBlank String name,
          @Email @NotBlank String email){
}

