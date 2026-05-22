package com.orderList.orderList.utils.mapper;

import com.orderList.orderList.model.dto.request.category.CreateCategoryDTO;
import com.orderList.orderList.model.dto.response.CategoryDTO;
import com.orderList.orderList.model.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDTO(Category category);
    Category toEntity(CreateCategoryDTO dto);
}
