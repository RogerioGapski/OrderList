package com.orderlist.api.services;

import com.orderlist.api.exceptions.customs.AlreadyExistsException;
import com.orderlist.api.exceptions.customs.NotFoundException;
import com.orderlist.api.model.dto.request.category.CreateCategoryDTO;
import com.orderlist.api.model.dto.request.category.UpdateCategoryDTO;
import com.orderlist.api.model.dto.response.CategoryDTO;
import com.orderlist.api.model.entities.Category;
import com.orderlist.api.repository.CategoryRepository;
import com.orderlist.api.utils.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryDTO createCategory(CreateCategoryDTO dto){
        if(categoryRepository.existsByName(dto.name())){
            throw new AlreadyExistsException("Category already exists.");
        }
        Category category =  categoryMapper.toEntity(dto);
        categoryRepository.save(category);
        return categoryMapper.toDTO(category);
    }

    @Transactional
    public void deleteCategory(Long id){
        findCategoryById(id);
        categoryRepository.deleteById(id);
    }

    public CategoryDTO findById(Long id){
        return categoryMapper.toDTO(findCategoryById(id));
    }

    public List<CategoryDTO> findAll(){
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, UpdateCategoryDTO dto){
        Category category = findCategoryById(id);
        if(categoryRepository.existsByName(dto.name())){
            throw new AlreadyExistsException("Category name already exists.");
        }
        category.setName(dto.name());
        categoryRepository.save(category);
        return categoryMapper.toDTO(category);
    }

    //Auxiliary method
    Category findCategoryById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found."));
    }
}
