package com.orderlist.api.utils.mapper;

import com.orderlist.api.model.dto.request.item.CreateItemDTO;
import com.orderlist.api.model.dto.response.ItemDTO;
import com.orderlist.api.model.entities.Item;
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
