package com.orderList.orderList.controllers;

import com.orderList.orderList.model.dto.request.product.*;
import com.orderList.orderList.model.dto.response.ProductDTO;
import com.orderList.orderList.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @RequestBody @Valid CreateProductDTO dto) {
        ProductDTO product = productService.createProduct(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.id())
                .toUri();
        return ResponseEntity.created(uri).body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDTO> findByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.findByName(name));
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ProductDTO>> findByCategory(
            @PathVariable String categoryName) {
        return ResponseEntity.ok(productService.findByCategory(categoryName));
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<ProductDTO> updatePrice(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProductPrice dto) {
        return ResponseEntity.ok(productService.updatePrice(id, dto));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductDTO> updateStock(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProductStock dto) {
        return ResponseEntity.ok(productService.updateStock(id, dto));
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<ProductDTO> updateName(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProductName dto) {
        return ResponseEntity.ok(productService.updateName(id, dto));
    }

    @PatchMapping("/{id}/category")
    public ResponseEntity<ProductDTO> updateCategory(
            @PathVariable Long id,
            @RequestParam Long categoryId) {
        return ResponseEntity.ok(productService.updateCategory(id, categoryId));
    }
}