package com.orderList.orderList.mapper;

import com.orderList.orderList.domain.entities.Product;
import com.orderList.orderList.dto.request.CreateOrderDTO;
import com.orderList.orderList.dto.response.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toEntity(CreateOrderDTO dto);
}
