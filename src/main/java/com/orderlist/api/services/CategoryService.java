package com.orderlist.api.services;

import com.orderlist.api.exceptions.customs.AlreadyExistsException;
import com.orderlist.api.exceptions.customs.ConflictException;
import com.orderlist.api.exceptions.customs.NotFoundException;
import com.orderlist.api.model.dto.request.category.CreateCategoryDTO;
import com.orderlist.api.model.dto.request.category.UpdateCategoryDTO;
import com.orderlist.api.model.dto.response.CategoryDTO;
import com.orderlist.api.model.entities.Category;
import com.orderlist.api.repository.CategoryRepository;
import com.orderlist.api.repository.ProductRepository;
import com.orderlist.api.utils.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDTO createCategory(CreateCategoryDTO dto){
        if(categoryRepository.existsByName(dto.name())){
            throw new AlreadyExistsException("Category already exists");
        }
        Category category = categoryMapper.toEntity(dto);
        Category categorySaved = categoryRepository.save(category);
        return categoryMapper.toDTO(categorySaved);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(Long id){
        findCategoryById(id);
        if(productRepository.existsByCategoryId(id)){
            throw new ConflictException("Category has products attached and cannot be deleted");
        }
        categoryRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('USER')")
    public CategoryDTO findById(Long id){
        return categoryMapper.toDTO(findCategoryById(id));
    }

    @PreAuthorize("hasRole('USER')")
    public Page<CategoryDTO> findAll(Pageable pageable){
        Page<Category> all = categoryRepository.findAll(pageable);
        return all.map(categoryMapper::toDTO);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDTO updateCategory(Long id, UpdateCategoryDTO dto){
        Category category = findCategoryById(id);
        if(categoryRepository.existsByName(dto.name())){
            throw new AlreadyExistsException("Category name already exists");
        }
        category.setName(dto.name());
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    //Auxiliary method
    Category findCategoryById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }
}
