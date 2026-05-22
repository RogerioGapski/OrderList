package com.orderList.orderList.utils.mapper;

import com.orderList.orderList.model.dto.request.items.CreateItemsDTO;
import com.orderList.orderList.model.dto.response.ItemsDTO;
import com.orderList.orderList.model.entities.Items;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemsMapper {
    ItemsDTO toDTO(Items items);
    Items toEntity(CreateItemsDTO dto);
}
