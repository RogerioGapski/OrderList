package com.orderList.orderList.utils.mapper;

import com.orderList.orderList.model.dto.request.product.CreateProductDTO;
import com.orderList.orderList.model.entities.Product;
import com.orderList.orderList.model.dto.response.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "items", ignore = true)
    Product toEntity(CreateProductDTO dto);
}
