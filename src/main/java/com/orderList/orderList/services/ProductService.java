package com.orderList.orderList.services;

import com.orderList.orderList.model.dto.request.product.CreateProductDTO;
import com.orderList.orderList.model.dto.request.product.UpdateProductName;
import com.orderList.orderList.model.dto.request.product.UpdateProductPrice;
import com.orderList.orderList.model.dto.request.product.UpdateProductStock;
import com.orderList.orderList.model.entities.Product;
import com.orderList.orderList.model.dto.response.ProductDTO;
import com.orderList.orderList.exceptions.customs.NotFoundException;
import com.orderList.orderList.utils.mapper.ProductMapper;
import com.orderList.orderList.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductDTO createProduct(CreateProductDTO p) {
        Product product = productMapper.toEntity(p);
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Transactional
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

    @Transactional
    public ProductDTO updatePrice(Long id, UpdateProductPrice newPrice) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        product.setPrice(newPrice.price());
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO updateStock(Long id, UpdateProductStock newStock){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        product.setStock(newStock.stock());
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO updateName(Long id, UpdateProductName newName) {
        Product product =  productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        product.setName(newName.name());
        productRepository.save(product);
        return productMapper.toDTO(product);
    }
}
