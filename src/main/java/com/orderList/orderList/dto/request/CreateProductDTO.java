package com.orderList.orderList.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateProductDTO {
    public record createProductDTO(
            @NotBlank String name){
    }
}
