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
public class ProductDTO {

    @NotBlank
    private String name;

    @NotNull
    private Double price;

    @NotNull
    private Integer stock;
}
