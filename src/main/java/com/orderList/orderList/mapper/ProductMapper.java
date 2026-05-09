package com.orderList.orderList.mapper;

import com.orderList.orderList.domain.entities.Order;
import com.orderList.orderList.domain.entities.Product;
import com.orderList.orderList.dto.request.CreateOrderDTO;
import com.orderList.orderList.dto.response.OrderDTO;
import com.orderList.orderList.dto.response.ProductDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toEntity(CreateOrderDTO dto);
}
