package com.orderList.orderList.model.dto.response;

import java.util.UUID;

public record UserDTO(
          UUID id,
          String name,
          String email){
}

