package com.orderList.orderList.services;

import com.orderList.orderList.model.entities.Product;
import com.orderList.orderList.model.dto.request.CreateProductDTO;
import com.orderList.orderList.model.dto.response.ProductDTO;
import com.orderList.orderList.exceptions.customs.NotFoundException;
import com.orderList.orderList.utils.mapper.ProductMapper;
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

    public void deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        productRepository.delete(product);
    }

    public ProductDTO findById(Long id) {
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
