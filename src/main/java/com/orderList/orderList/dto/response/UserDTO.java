package com.orderList.orderList.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@Builder
@ToString
public class UserDTO {
    public record userDTO(
          @NotBlank String name,
          @NotBlank String email){
    }
}
