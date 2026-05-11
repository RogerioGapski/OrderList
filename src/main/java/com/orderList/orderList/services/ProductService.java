package com.orderList.orderList.services;

import com.orderList.orderList.domain.entities.Product;
import com.orderList.orderList.dto.request.CreateProductDTO;
import com.orderList.orderList.dto.response.ProductDTO;
import com.orderList.orderList.mapper.ProductMapper;
import com.orderList.orderList.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDTO createProduct(CreateProductDTO dto) {
        Product product = productMapper.toEntity(dto);
        productRepository.save(product);
        return productMapper.toDTO(product);
    }
}
