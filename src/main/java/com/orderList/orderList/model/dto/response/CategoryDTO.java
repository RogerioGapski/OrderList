package com.orderList.orderList.model.dto.response;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(
        @NotBlank String category
){
}
