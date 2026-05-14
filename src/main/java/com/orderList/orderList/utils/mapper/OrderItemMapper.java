package com.orderList.orderList.utils.mapper;

import com.orderList.orderList.model.entities.OrderItem;
import com.orderList.orderList.model.dto.request.CreateOrderItemDTO;
import com.orderList.orderList.model.dto.response.OrderItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemDTO toDTO(OrderItem orderItem);
    OrderItem toEntity(CreateOrderItemDTO dto);
}
