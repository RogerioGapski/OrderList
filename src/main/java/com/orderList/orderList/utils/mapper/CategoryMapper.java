package com.orderList.orderList.utils.mapper;

import com.orderList.orderList.model.dto.request.category.CreateCategoryDTO;
import com.orderList.orderList.model.dto.response.CategoryDTO;
import com.orderList.orderList.model.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDTO(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toEntity(CreateCategoryDTO dto);
}
