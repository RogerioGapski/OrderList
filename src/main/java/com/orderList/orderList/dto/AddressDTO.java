package com.orderList.orderList.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AddressDTO {

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotNull
    private Integer number;
}
