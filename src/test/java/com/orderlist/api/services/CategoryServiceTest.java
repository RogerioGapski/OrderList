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
import com.orderlist.api.utils.mapper.CategoryMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    CategoryService categoryService;

    CategoryMapper categoryMapper;

    @Nested
    class createCategory {

        @BeforeEach
        void setup(){
            categoryMapper = new CategoryMapperImpl();
            categoryService = new CategoryService(categoryMapper, categoryRepository, productRepository);
        }

        @Test
        @DisplayName("Should create a new category when the name does not exist")
        void shouldCreateCategoryWhenTheNameDoesNotExist() {
            var dto = new CreateCategoryDTO("Books");
            var savedCategory = new Category(10L, "Books", List.of());

            when(categoryRepository.existsByName(dto.name()))
                    .thenReturn(false);

            when(categoryRepository.save(any(Category.class)))
                    .thenReturn(savedCategory);

            var result = categoryService.createCategory(dto);

            verify(categoryRepository).existsByName(dto.name());
            verify(categoryRepository).save(
                    assertArg(category -> category.getName().equals(dto.name())));

            assertThat(result.name())
                    .isEqualTo(savedCategory.getName());
        }

        @Test
        @DisplayName("Should throw exception when the name exists")
        void shouldThrowExceptionWhenTheNameExists() {
            var dto = new CreateCategoryDTO("Books");

            when(categoryRepository.existsByName(dto.name()))
                    .thenReturn(true);

            AlreadyExistsException exception = assertThrows(
                    AlreadyExistsException.class,
                    () -> categoryService.createCategory(dto)
            );

            verify(categoryRepository).existsByName(dto.name());
            verify(categoryRepository, never()).save(any(Category.class));

            assertThat(exception.getMessage())
                    .isEqualTo("Category already exists");
        }
    }

    @Nested
    class deleteCategory {

        @BeforeEach
        void setup(){
            categoryService = new CategoryService(categoryMapper, categoryRepository, productRepository);
        }

        @Test
        @DisplayName("Should delete the category if there are no product attached to it")
        void shouldDeleteTheCategoryIfThereAreNoProductsAttachedToIt() {
            var category = new Category(10L, "Books", List.of());

            when(categoryRepository.findById(category.getId()))
                    .thenReturn(Optional.of(category));

            when(productRepository.existsByCategoryId(category.getId()))
                    .thenReturn(false);

            categoryService.deleteCategory(category.getId());

            verify(categoryRepository).findById(category.getId());
            verify(productRepository).existsByCategoryId(category.getId());
            verify(categoryRepository).deleteById(category.getId());
        }

        @Test
        @DisplayName("Should throw exception when category not found by id on delete")
        void shouldThrowExceptionWhenCategoryNotFoundByIdOnDelete() {
            when(categoryRepository.findById(10L))
                    .thenReturn(Optional.empty());

            NotFoundException e = assertThrows(
                    NotFoundException.class,
                    () -> categoryService.deleteCategory(10L));

            verify(categoryRepository).findById(10L);
            verify(productRepository, never()).existsByCategoryId(any());
            verify(categoryRepository, never()).deleteById(any());

            assertThat(e.getMessage())
                    .isEqualTo("Category not found");
        }

        @Test
        @DisplayName("Should throw exception when category has product attached")
        void shouldThrowExceptionWhenCategoryHasProductAttached() {
            var category = new Category(10L, "Books", List.of());

            when(categoryRepository.findById(category.getId()))
                    .thenReturn(Optional.of(category));

            when(productRepository.existsByCategoryId(category.getId()))
                    .thenReturn(true);

            ConflictException e = assertThrows(
                    ConflictException.class,
                    () -> categoryService.deleteCategory(category.getId())
            );

            verify(categoryRepository).findById(category.getId());
            verify(productRepository).existsByCategoryId(category.getId());
            verify(categoryRepository, never()).deleteById(any());

            assertThat(e.getMessage()).
                    isEqualTo("Category has products attached and cannot be deleted");
        }
    }

    @Nested
    class findById {

        @BeforeEach
        void setup(){
            categoryMapper = new CategoryMapperImpl();
            categoryService = new CategoryService(categoryMapper, categoryRepository, productRepository);
        }

        @Test
        @DisplayName("Should return a category when the ID is found")
        void shouldReturnACategoryWhenTheIDIsFound() {
            var expected = new Category(10L, "Books", List.of());

            when(categoryRepository.findById(expected.getId()))
                    .thenReturn(Optional.of(expected));

            CategoryDTO result = categoryService.findById(expected.getId());

            verify(categoryRepository).findById(expected.getId());

            assertThat(expected.getId())
                    .isEqualTo(result.id());

            assertThat(expected.getName())
                    .isEqualTo(result.name());
        }

        @Test
        @DisplayName("Should throw exception when the ID is not found")
        void shouldThrowExceptionWhenTheIDIsNotFound() {
            when(categoryRepository.findById(10L))
                    .thenReturn(Optional.empty());

            NotFoundException e = assertThrows(
                    NotFoundException.class,
                    () -> categoryService.findById(10L)
            );

            verify(categoryRepository).findById(10L);

            assertThat(e.getMessage()).isEqualTo("Category not found");
        }
    }

    @Nested
    class findAll {

        @BeforeEach
        void setup(){
            categoryMapper = new CategoryMapperImpl();
            categoryService = new CategoryService(categoryMapper, categoryRepository, productRepository);
        }

        @Test
        @DisplayName("Should return all categories")
        void shouldReturnAllCategories() {
            var pageable = PageRequest.of(0, 10);
            var category1 = new Category(1L, "Books", List.of());
            var category2 = new Category(2L, "Fruits", List.of());
            Page<Category> pageMock = new PageImpl<>(List.of(category1, category2), pageable, 2);

            when(categoryRepository.findAll(pageable)).thenReturn(pageMock);

            Page<CategoryDTO> result = categoryService.findAll(pageable);

            verify(categoryRepository).findAll(pageable);

            assertThat(result.getContent())
                    .hasSize(2);

            assertThat(result.getContent())
                    .extracting(CategoryDTO::name)
                    .containsExactly(category1.getName(), category2.getName());
        }

        @Test
        @DisplayName("Should return an empty list if no categories exist")
        void shouldReturnAnEmptyListIfNoCategoriesExist() {
            var pageable = PageRequest.of(0, 10);
            Page<Category> pageMock = new PageImpl<>(List.of(), pageable, 0);

            when(categoryRepository.findAll(pageable)).thenReturn(pageMock);

            Page<CategoryDTO> result = categoryService.findAll(pageable);

            verify(categoryRepository).findAll(pageable);

            assertThat(result.getContent())
                    .isEmpty();
        }
    }

    @Nested
    class updateCategory {

        @BeforeEach
        void setup() {
            categoryMapper = new CategoryMapperImpl();
            categoryService = new CategoryService(categoryMapper, categoryRepository, productRepository);
        }

        @Test
        @DisplayName("Should update the category when the new name does not exist")
        void shouldUpdateTheCategoryWhenTheNewNameDoesNotExist(){
            var categoryUp = new Category(10L, "Books", List.of());
            var updateCategoryDTO = new UpdateCategoryDTO("Fruits");

            when(categoryRepository.findById(categoryUp.getId()))
                    .thenReturn(Optional.of(categoryUp));

            when(categoryRepository.existsByName(updateCategoryDTO.name()))
                    .thenReturn(false);

            when(categoryRepository.save(any(Category.class)))
                    .thenReturn(categoryUp);

            CategoryDTO categoryUpdated = categoryService.updateCategory(
                    categoryUp.getId(), updateCategoryDTO);

            verify(categoryRepository).findById(categoryUp.getId());
            verify(categoryRepository).existsByName(updateCategoryDTO.name());
            verify(categoryRepository).save(
                    assertArg(category -> category.getName().equals(updateCategoryDTO.name()))
            );

            assertThat(updateCategoryDTO.name())
                    .isEqualTo(categoryUpdated.name());
        }

        @Test
        @DisplayName("Should throw exception if the category does not exist")
        void shouldThrowExceptionWhenTheCategoryDoesNotExist(){
            var updateCategoryDTO = new UpdateCategoryDTO("Fruits");

            when(categoryRepository.findById(10L))
                    .thenReturn(Optional.empty());

            NotFoundException e = assertThrows(
                    NotFoundException.class,
                    () -> categoryService.updateCategory(10L, updateCategoryDTO)
            );

            verify(categoryRepository).findById(10L);
            verify(categoryRepository, never()).existsByName(updateCategoryDTO.name());
            verify(categoryRepository, never()).save(any(Category.class));

            assertThat(e.getMessage())
                    .isEqualTo("Category not found");
        }

        @Test
        @DisplayName("Should throw exception when new category name already exists")
        void shouldThrowExceptionWhenTheCategoryNameAlreadyExists(){
            var updateCategoryDTO = new UpdateCategoryDTO("Fruits");

            when(categoryRepository.findById(10L))
                    .thenReturn(Optional.of(new Category()));

            when(categoryRepository.existsByName(updateCategoryDTO.name()))
                    .thenReturn(true);

            AlreadyExistsException e = assertThrows(
                    AlreadyExistsException.class,
                    () -> categoryService.updateCategory(10L, updateCategoryDTO)
            );

            verify(categoryRepository).findById(10L);
            verify(categoryRepository).existsByName(updateCategoryDTO.name());
            verify(categoryRepository, never()).save(any(Category.class));

            assertThat(e.getMessage())
                    .isEqualTo("Category name already exists");
        }
    }
}