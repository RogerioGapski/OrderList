package com.orderList.orderList.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public record UpdateAddressDTO(
        @NotBlank String city,
        @NotBlank String street,
        @NotBlank @Pattern(
                regexp = "^[0-9]+$", message = "The house number should contain only numbers."
        ) String number){
}

