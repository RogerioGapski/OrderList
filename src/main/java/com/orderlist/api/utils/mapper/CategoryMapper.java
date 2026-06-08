package com.orderlist.api.utils.mapper;

import com.orderlist.api.model.dto.request.category.CreateCategoryDTO;
import com.orderlist.api.model.dto.response.CategoryDTO;
import com.orderlist.api.model.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDTO(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toEntity(CreateCategoryDTO dto);
}
