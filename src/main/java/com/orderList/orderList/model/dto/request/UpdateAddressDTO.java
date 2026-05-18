package com.orderList.orderList.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public record UpdateAddressDTO(
        @NotBlank String city,
        @NotBlank String street,
        @NotNull Integer number){
}

