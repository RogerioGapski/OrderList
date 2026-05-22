package com.orderList.orderList.controllers;

import com.orderList.orderList.model.dto.request.category.UpdateCategoryDTO;
import com.orderList.orderList.model.dto.request.product.*;
import com.orderList.orderList.model.dto.response.ProductDTO;
import com.orderList.orderList.model.entities.Category;
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
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @RequestBody @Valid CreateProductDTO productDTO
    ){
        ProductDTO product = productService.createProduct(productDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.id())
                .toUri();

        return ResponseEntity.created(uri).body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id
    ){
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok().body(productService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDTO> getByName(
            @PathVariable String name
    ){
        return ResponseEntity.ok().body(productService.findByName(name));
    }

    @GetMapping("/products/{category}")
    public ResponseEntity<List<ProductDTO>> getByCategory(
            @PathVariable String category
    ){
        return ResponseEntity.ok().body(productService.findByCategory(category));
    }

    @PatchMapping("/price/{id}")
    public ResponseEntity<ProductDTO> updatePrice(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProductPrice newPrice
    ){
        return ResponseEntity.ok().body(productService.updatePrice(id, newPrice));
    }

    @PatchMapping("/stock/{id}")
    public ResponseEntity<ProductDTO> updateStock(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProductStock newStock
    ){
        return ResponseEntity.ok().body(productService.updateStock(id, newStock));
    }

    @PatchMapping("/product/{id}")
    public ResponseEntity<ProductDTO> updateName(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProductName newName
    ){
        return ResponseEntity.ok().body(productService.updateName(id, newName));
    }

    @PatchMapping("/category/{id}")
    public ResponseEntity<ProductDTO> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid Category newCategory
    ){
        return ResponseEntity.ok().body(productService.updateCategory(id, newCategory));
    }
}
