package com.orderList.orderList.mapper;

import com.orderList.orderList.domain.entities.OrderItem;
import com.orderList.orderList.dto.request.CreateOrderItemDTO;
import com.orderList.orderList.dto.response.OrderItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemDTO toDTO(OrderItem orderItem);
    OrderItem toEntity(CreateOrderItemDTO dto);
}
