package com.orderList.orderList.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@Builder
@ToString
public class AddressDTO {
    public record addressDTO(
            @NotBlank String city,
            @NotBlank String street,
            @NotNull Integer number){
    }
}
