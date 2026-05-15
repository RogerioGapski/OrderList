package com.orderList.orderList.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChangeUserNameDTO(
        @NotBlank String name){
}
