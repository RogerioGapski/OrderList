package com.orderlist.api.utils.mapper;

import com.orderlist.api.model.dto.request.order.CreateOrderDTO;
import com.orderlist.api.model.entities.Order;
import com.orderlist.api.model.dto.response.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDTO(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "items", ignore = true)
    Order toEntity(CreateOrderDTO dto);
}
