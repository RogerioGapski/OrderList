package com.orderList.orderList.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String email;
}
