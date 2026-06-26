package com.orderlist.api.services;

import com.orderlist.api.model.dto.request.product.*;
import com.orderlist.api.model.entities.Category;
import com.orderlist.api.model.entities.Product;
import com.orderlist.api.model.dto.response.ProductDTO;
import com.orderlist.api.exceptions.customs.NotFoundException;
import com.orderlist.api.utils.mapper.ProductMapper;
import com.orderlist.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;

    @Transactional
    public ProductDTO createProduct(CreateProductDTO dto) {
        Category category = categoryService.findCategoryById(dto.categoryId());
        Product product = productMapper.toEntity(dto);
        product.setCategory(category);
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Transactional
    public void deleteById(Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    public ProductDTO findById(Long id) {
        return productMapper.toDTO(findProductById(id));
    }

    public ProductDTO findByName(String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        return productMapper.toDTO(product);
    }

    public List<ProductDTO> findByCategory(String categoryName) {

        List<Product> products = productRepository.findByCategoryName(categoryName);
        return products.stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Transactional
    public ProductDTO updatePrice(Long id, UpdateProductPrice dto) {
        Product product = findProductById(id);
        product.setPrice(dto.price());
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO updateStock(Long id, UpdateProductStock dto){
        Product product = findProductById(id);
        product.setStock(dto.stock());
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO updateName(Long id, UpdateProductName dto) {
        Product product = findProductById(id);
        product.setName(dto.name());
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO updateCategory(Long id, Long categoryId) {
        Product product = findProductById(id);
        Category category = categoryService.findCategoryById(categoryId);
        product.setCategory(category);
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    //Auxiliary method
    public Product findProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found."));
    }
}
