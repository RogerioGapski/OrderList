package com.orderlist.api.model.dto.response;

import java.util.UUID;

public record AddressDTO(
            UUID userId,
            Long id,
            String city,
            String street,
            String number){
}

