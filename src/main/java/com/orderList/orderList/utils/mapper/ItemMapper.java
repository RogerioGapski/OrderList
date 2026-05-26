package com.orderList.orderList.utils.mapper;

import com.orderList.orderList.model.dto.request.item.CreateItemDTO;
import com.orderList.orderList.model.dto.response.ItemDTO;
import com.orderList.orderList.model.entities.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemDTO toDTO(Item item);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "unitaryPrice", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "user", ignore = true)
    Item toEntity(CreateItemDTO dto);
}
