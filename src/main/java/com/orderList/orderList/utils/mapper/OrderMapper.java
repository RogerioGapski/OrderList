package com.orderList.orderList.utils.mapper;

import com.orderList.orderList.model.dto.request.order.CreateOrderDTO;
import com.orderList.orderList.model.entities.Order;
import com.orderList.orderList.model.dto.request.CreateOrderDTO;
import com.orderList.orderList.model.dto.response.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDTO(Order order);
    Order toEntity(CreateOrderDTO dto);
}
