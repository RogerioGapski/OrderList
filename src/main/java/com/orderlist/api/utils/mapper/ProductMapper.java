package com.orderlist.api.utils.mapper;

import com.orderlist.api.model.dto.request.product.CreateProductDTO;
import com.orderlist.api.model.entities.Product;
import com.orderlist.api.model.dto.response.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "item", ignore = true)
    Product toEntity(CreateProductDTO dto);
}
