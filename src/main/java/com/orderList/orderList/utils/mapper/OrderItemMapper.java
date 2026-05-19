package com.orderList.orderList.utils.mapper;

import com.orderList.orderList.model.dto.request.CreateItemsDTO;
import com.orderList.orderList.model.dto.response.ItemsDTO;
import com.orderList.orderList.model.entities.Items;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    ItemsDTO toDTO(Items items);
    Items toEntity(CreateItemsDTO dto);
}
