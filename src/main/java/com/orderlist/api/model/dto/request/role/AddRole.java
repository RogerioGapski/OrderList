package com.orderlist.api.model.dto.request.role;

import com.orderlist.api.model.entities.Role;
import jakarta.validation.constraints.NotBlank;

public record AddRole(
        @NotBlank Role role) {
}
