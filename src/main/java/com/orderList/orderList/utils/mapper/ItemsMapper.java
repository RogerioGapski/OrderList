package com.orderList.orderList.utils.mapper;

import com.orderList.orderList.model.dto.request.items.CreateItemsDTO;
import com.orderList.orderList.model.dto.response.ItemsDTO;
import com.orderList.orderList.model.entities.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemsMapper {
    ItemsDTO toDTO(Item item);
    Item toEntity(CreateItemsDTO dto);
}
