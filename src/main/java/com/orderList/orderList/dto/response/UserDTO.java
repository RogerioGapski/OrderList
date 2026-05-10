package com.orderList.orderList.dto.response;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(
          @NotBlank String name,
          @NotBlank String email){
}

