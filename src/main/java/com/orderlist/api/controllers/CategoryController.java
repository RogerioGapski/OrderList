package com.orderlist.api.controllers;

import com.orderlist.api.model.dto.request.category.CreateCategoryDTO;
import com.orderlist.api.model.dto.request.category.UpdateCategoryDTO;
import com.orderlist.api.model.dto.response.CategoryDTO;
import com.orderlist.api.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/admin/create")
    public ResponseEntity<CategoryDTO> createCategory(
            @RequestBody @Valid CreateCategoryDTO dto) {
        CategoryDTO category = categoryService.createCategory(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.id())
                .toUri();
        return ResponseEntity.created(uri).body(category);
    }

    @DeleteMapping("admin/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PatchMapping("admin/update/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCategoryDTO dto) {
        return ResponseEntity.ok(categoryService.updateCategory(id, dto));
    }
}

