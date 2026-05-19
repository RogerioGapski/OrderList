package com.orderList.orderList.model.dto.request.user;

import jakarta.validation.constraints.NotBlank;

public record ChangeUserNameDTO(
        @NotBlank String name){
}
