package com.orderList.orderList.utils.mapper;

import com.orderList.orderList.model.dto.request.product.CreateProductDTO;
import com.orderList.orderList.model.entities.Product;
import com.orderList.orderList.model.dto.response.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toEntity(CreateProductDTO dto);
}
