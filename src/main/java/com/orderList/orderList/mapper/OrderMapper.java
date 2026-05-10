package com.orderList.orderList.mapper;

import com.orderList.orderList.domain.entities.Order;
import com.orderList.orderList.dto.request.CreateOrderDTO;
import com.orderList.orderList.dto.response.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDTO(Order order);
    Order toEntity(CreateOrderDTO dto);
}
