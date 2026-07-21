package com.orderlist.api.controllers;

import com.orderlist.api.config.swagger.*;
import com.orderlist.api.model.dto.request.product.*;
import com.orderlist.api.model.dto.response.ProductDTO;
import com.orderlist.api.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "jwtAuth")
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Creates a product")
    @ApiProtectedCreateResponses
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

    @Operation(summary = "Deletes a product by ID")
    @ApiProtectedDeleteResponses
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Finds a product by ID")
    @ApiProtectedReadResponses
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @Operation(summary = "Finds a product by name")
    @ApiProtectedReadResponses
    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDTO> findByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.findByName(name));
    }

    @Operation(summary = "Finds products by category")
    @ApiProtectedReadResponses
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<Page<ProductDTO>> findByCategory(
            @PathVariable String categoryName, Pageable pageable) {
        return ResponseEntity.ok(productService.findByCategory(categoryName, pageable));
    }

    @Operation(summary = "Updates the product price by ID")
    @ApiProtectedUpdateResponses
    @PatchMapping("/{id}/price")
    public ResponseEntity<ProductDTO> updatePrice(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProductPrice dto) {
        return ResponseEntity.ok(productService.updatePrice(id, dto));
    }

    @Operation(summary = "Updates the product stock by ID")
    @ApiProtectedUpdateResponses
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductDTO> updateStock(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProductStock dto) {
        return ResponseEntity.ok(productService.updateStock(id, dto));
    }

    @Operation(summary = "Updates the product name by ID")
    @ApiProtectedUpdateResponses
    @PatchMapping("/{id}/name")
    public ResponseEntity<ProductDTO> updateName(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProductName dto) {
        return ResponseEntity.ok(productService.updateName(id, dto));
    }

    @Operation(summary = "Updates the product category")
    @ApiProtectedUpdateResponses
    @PatchMapping("/{id}/category")
    public ResponseEntity<ProductDTO> updateCategory(
            @PathVariable Long id,
            @RequestParam Long categoryId) {
        return ResponseEntity.ok(productService.updateCategory(id, categoryId));
    }
}
