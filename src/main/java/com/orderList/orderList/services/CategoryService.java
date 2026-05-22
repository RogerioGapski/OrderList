package com.orderList.orderList.services;

import com.orderList.orderList.exceptions.customs.NotFoundException;
import com.orderList.orderList.model.dto.request.category.CreateCategoryDTO;
import com.orderList.orderList.model.dto.request.category.UpdateCategoryDTO;
import com.orderList.orderList.model.dto.response.CategoryDTO;
import com.orderList.orderList.model.entities.Category;
import com.orderList.orderList.repository.CategoryRepository;
import com.orderList.orderList.utils.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryDTO createCategory(CreateCategoryDTO dto){
        Category category =  categoryMapper.toEntity(dto);
        categoryRepository.save(category);
        return categoryMapper.toDTO(category);
    }

    @Transactional
    public void deleteCategory(Long id){
        findByIdMethod(id);
        categoryRepository.deleteById(id);
    }

    public CategoryDTO findById(Long id){
        Category category = findByIdMethod(id);
        return categoryMapper.toDTO(category);
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, UpdateCategoryDTO dto){
        Category category = findByIdMethod(id);
        category.setName(dto.name());
        categoryRepository.save(category);
        return categoryMapper.toDTO(category);
    }

    public Category findByIdMethod(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }
}
