package com.orderList.orderList.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@Builder
@ToString
public class ProductDTO {
    public record productDTO(
            @NotBlank String name,
            @NotNull Double price,
            @NotNull Integer stock){
    }
}
