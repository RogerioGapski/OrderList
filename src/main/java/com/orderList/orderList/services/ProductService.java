package com.orderList.orderList.services;

import com.orderList.orderList.model.dto.request.category.UpdateCategoryDTO;
import com.orderList.orderList.model.dto.request.product.*;
import com.orderList.orderList.model.entities.Category;
import com.orderList.orderList.model.entities.Product;
import com.orderList.orderList.model.dto.response.ProductDTO;
import com.orderList.orderList.exceptions.customs.NotFoundException;
import com.orderList.orderList.utils.mapper.ProductMapper;
import com.orderList.orderList.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Product product = findByIdMethod(id);
        productRepository.delete(product);
    }

    public ProductDTO findById(Long id) {
        Product product = findByIdMethod(id);
        return productMapper.toDTO(product);
    }

    public ProductDTO findByName(String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        return productMapper.toDTO(product);
    }

    public List<ProductDTO> findByCategory(String category) {
        List<Product> products =  productRepository.findByCategory(category);
        return products.stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Transactional
    public ProductDTO updatePrice(Long id, UpdateProductPrice newPrice) {
        Product product = findByIdMethod(id);
        product.setPrice(newPrice.price());
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO updateStock(Long id, UpdateProductStock newStock){
        Product product = findByIdMethod(id);
        product.setStock(newStock.stock());
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO updateName(Long id, UpdateProductName newName) {
        Product product = findByIdMethod(id);
        product.setName(newName.name());
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO updateCategory(Long id, Category newCategory) {
        Product product = findByIdMethod(id);
        product.setCategory(newCategory);
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    public Product findByIdMethod(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }
}
