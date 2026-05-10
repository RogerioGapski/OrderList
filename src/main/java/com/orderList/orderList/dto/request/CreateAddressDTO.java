package com.orderList.orderList.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public record CreateAddressDTO(
        @NotBlank String city,
        @NotBlank String street,
        @NotNull Integer number){
}

