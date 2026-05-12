package com.orderList.orderList.services;

import com.orderList.orderList.domain.entities.Product;
import com.orderList.orderList.dto.request.CreateProductDTO;
import com.orderList.orderList.dto.response.ProductDTO;
import com.orderList.orderList.exceptions.NotFoundException;
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

    public void deleteById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        productRepository.delete(product);
    }

    public ProductDTO findById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        return productMapper.toDTO(product);
    }

    public ProductDTO findByName(String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        return productMapper.toDTO(product);
    }
}
