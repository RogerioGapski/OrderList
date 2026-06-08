package com.orderlist.api.model.dto.response;

import java.util.UUID;

public record UserDTO(
          UUID id,
          String name,
          String email){
}

