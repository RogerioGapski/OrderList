package com.orderlist.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderlist.api.exceptions.customs.ConflictException;
import com.orderlist.api.exceptions.customs.NotFoundException;
import com.orderlist.api.model.dto.request.product.CreateProductDTO;
import com.orderlist.api.model.dto.request.product.UpdateProductName;
import com.orderlist.api.model.dto.request.product.UpdateProductPrice;
import com.orderlist.api.model.dto.request.product.UpdateProductStock;
import com.orderlist.api.model.dto.response.CategoryDTO;
import com.orderlist.api.model.dto.response.ProductDTO;
import com.orderlist.api.services.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Nested
    class createProduct {

        @Test
        @DisplayName("Should create product when body is valid")
        void shouldCreateProductWhenBodyIsValid() throws Exception {
            var request = new CreateProductDTO(
                    "Banana", 20L, new BigDecimal("10.0"), 10);
            var response = new ProductDTO(
                    10L, "Banana", new CategoryDTO(20L, "Fruit"), new BigDecimal("10.0"), 10);

            when(productService.createProduct(request))
                    .thenReturn(response);

            mockMvc.perform(
                            post("/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                    .andExpectAll(
                            status().isCreated(),
                            jsonPath("$.id").value(10),
                            jsonPath("$.name").value("Banana")
                    );

            verify(productService).createProduct(request);
            verifyNoMoreInteractions(productService);
        }

        @Test
        @DisplayName("Should return 400 when body is invalid")
        void shouldReturn400WhenBodyIsInvalid() throws Exception {
            String invalidJson = """
                    {
                        "id": "10",
                        "name": "Banana",
                        "category": "Fruit",
                        "price": "dez",
                        "stock": "10"
                    }
                    """;

            mockMvc.perform(
                            post("/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(invalidJson))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(productService);
        }

        @Test
        @DisplayName("Should return 400 when dto is invalid")
        void shouldReturn400WhenDtoIsInvalid() throws Exception {
            var request = new CreateProductDTO(
                    "", 20L, new BigDecimal("10.0"), 10
            );

            mockMvc.perform(
                            post("/products")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(productService);
        }
    }

    @Nested
    class deleteProduct {

        @Test
        @DisplayName("Should delete product when id exists")
        void shouldDeleteProductWhenIdExists() throws Exception {
            mockMvc.perform(
                            delete("/products/{id}", 10L))
                    .andExpect(status().isNoContent());

            verify(productService).deleteById(10L);
        }

        @Test
        @DisplayName("Should return 404 when id is not found")
        void shouldReturn404WhenIdIsNotFound() throws Exception {
            doThrow(new NotFoundException("Product not found"))
                    .when(productService)
                    .deleteById(10L);

            mockMvc.perform(
                            delete("/products/{id}", 10L))
                    .andExpectAll(
                            status().isNotFound(),
                            jsonPath("$.message").value("Product not found")
                    );

            verify(productService).deleteById(10L);
        }

        @Test
        @DisplayName("Should return 409 when product has items attached")
        void shouldReturn409WhenProductHasItemsAttached() throws Exception {
            doThrow(new ConflictException("Product has items attached and cannot be deleted"))
                    .when(productService)
                    .deleteById(10L);

            mockMvc.perform(
                            delete("/products/{id}", 10L))
                    .andExpectAll(
                            status().isConflict(),
                            jsonPath("$.message").value("Product has items attached and cannot be deleted")
                    );

            verify(productService).deleteById(10L);
        }


    }

    @Nested
    class findById {

        @Test
        @DisplayName("Should return product when id exists")
        void shouldReturnProductWhenIdExists() throws Exception {
            var dto = new ProductDTO(
                    10L, "Banana", new CategoryDTO(30L, "Fruit"), new BigDecimal("10.0"), 10);

            when(productService.findById(10L))
                    .thenReturn(dto);

            mockMvc.perform(
                            get("/products/{id}", 10L))
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.name").value("Banana")
                    );
        }

        @Test
        @DisplayName("Should return 404 when product is not found")
        void shouldReturn404WhenProductIsNotFound() throws Exception {
            when(productService.findById(99L))
                    .thenThrow(new NotFoundException("Product not found"));

            mockMvc.perform(
                            get("/products/{id}", 99L))
                    .andExpectAll(
                            status().isNotFound(),
                            jsonPath("$.message").value("Product not found")
                    );

            verify(productService).findById(99L);
        }

        @Test
        @DisplayName("Should return 400 when id is letter")
        void shouldReturn400WhenIdIsLetter() throws Exception {
            mockMvc.perform(
                            get("/products/{id}", "abc"))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(productService);
        }
    }

    @Nested
    class findByName {

        @Test
        @DisplayName("Should return product when name exists")
        void shouldReturnProductWhenNameExists() throws Exception {
            var dto = new ProductDTO(
                    10L, "Banana", new CategoryDTO(30L, "Fruit"), new BigDecimal("10.0"), 10);

            when(productService.findByName("Banana"))
                    .thenReturn(dto);

            mockMvc.perform(
                            get("/products/name/{name}", "Banana"))
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.id").value(10),
                            jsonPath("$.name").value("Banana")
                    );
        }

        @Test
        @DisplayName("Should return 404 when product is not found")
        void shouldReturn404WhenProductIsNotFound() throws Exception {
            when(productService.findByName("Banana"))
                    .thenThrow(new NotFoundException("Product not found"));

            mockMvc.perform(
                            get("/products/name/{name}", "Banana"))
                    .andExpectAll(
                            status().isNotFound(),
                            jsonPath("$.message").value("Product not found")
                    );

            verify(productService).findByName("Banana");
        }
    }

    @Nested
    class findByCategory {

        @Test
        @DisplayName("Should return product page when category exists")
        void shouldReturnProductPageWhenCategoryExists() throws Exception {
            var product = new ProductDTO(
                    10L, "Banana", new CategoryDTO(30L, "Fruit"), new BigDecimal("10.0"), 10);

            var page = new PageImpl<>(
                    List.of(product),
                    PageRequest.of(0, 10),
                    1);

            when(productService.findByCategory(
                    eq("Fruit"),
                    any(Pageable.class)
            )).thenReturn(page);

            mockMvc.perform(
                    get("/products/category/{categoryName}", "Fruit")
                    .param("page", "0")
                    .param("size", "10"))
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.content[0].id").value(10),
                            jsonPath("$.content[0].name").value("Banana"),
                            jsonPath("$.totalElements").value(1)
                    );

            verify(productService).findByCategory(
                    eq("Fruit"),
                    any(Pageable.class)
            );
        }

        @Test
        @DisplayName("Should return empty page when category has no products")
        void shouldReturnEmptyPageWhenCategoryHasNoProducts() throws Exception {
            var page = new PageImpl<ProductDTO>(
                    List.of(),
                    PageRequest.of(0, 10),
                    0
            );

            when(productService.findByCategory(
                    eq("Fruit"),
                    any(Pageable.class)
            )).thenReturn(page);

            mockMvc.perform(
                    get("/products/category/{categoryName}", "Fruit")
                    .param("page", "0")
                    .param("size", "10"))
                    .andExpectAll(
                        status().isOk(),
                        jsonPath("$.content").isEmpty(),
                        jsonPath("$.totalElements").value(0)
                    );

            verify(productService).findByCategory(
                    eq("Fruit"),
                    any(Pageable.class)
            );
        }
    }

    @Nested
    class updatePrice {

        @Test
        @DisplayName("Should update product price when body is valid")
        void shouldUpdateProductPriceWhenBodyIsValid() throws Exception {
            var product = new ProductDTO(
                    99L, "Banana", new CategoryDTO(10L, "Fruit"), new BigDecimal("10.0"), 10);
            var newPrice = new UpdateProductPrice(
                    new BigDecimal("10.0"));

            when(productService.updatePrice(99L, newPrice))
                    .thenReturn(product);

            mockMvc.perform(
                    patch("/products/{id}/price", 99)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newPrice)))
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.id").value(99),
                            jsonPath("$.price").value(10.0)
                    );

            verify(productService).updatePrice(99L, newPrice);
            verifyNoMoreInteractions(productService);
        }

        @Test
        @DisplayName("Should return 400 when body is invalid")
        void shouldReturn400WhenBodyIsInvalid() throws Exception {
            String invalidJson = """
                    {
                    "price": ""
                    }
                    """;

            mockMvc.perform(
                    patch("/products/{id}/price", 99)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(invalidJson))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(productService);
        }

        @Test
        @DisplayName("Should return 400 when dto is invalid")
        void shouldReturn400WhenDtoIsInvalid() throws Exception {
            var dto = new UpdateProductPrice(new BigDecimal("-1"));

            mockMvc.perform(
                    patch("/products/{id}/price", 99)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(productService);
        }

        @Test
        @DisplayName("Should return 404 when product is not found")
        void shouldReturn404WhenProductIsNotFound() throws Exception {
            var dto = new UpdateProductPrice(
                    new BigDecimal("10.0"));

            when(productService.updatePrice(10L, dto))
                    .thenThrow(new NotFoundException("Product not found"));

            mockMvc.perform(
                    patch("/products/{id}/price", 10)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpectAll(
                            status().isNotFound(),
                            jsonPath("$.message").value("Product not found")
                    );

            verify(productService).updatePrice(10L, dto);
            verifyNoMoreInteractions(productService);
        }

    }

    @Nested
    class updateStock {

        @Test
        @DisplayName("Should update product stock when body is valid")
        void shouldUpdateProductStockWhenBodyIsValid() throws Exception {
            var dto = new UpdateProductStock(10);
            var productUpdated = new ProductDTO(
                    99L, "Banana", new CategoryDTO(10L, "Fruit"), new BigDecimal("10.0"), 10);

            when(productService.updateStock(99L, dto))
                    .thenReturn(productUpdated);

            mockMvc.perform(
                    patch("/products/{id}/stock", 99)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.id").value(99),
                            jsonPath("$.stock").value(10)
                    );

            verify(productService).updateStock(99L, dto);
            verifyNoMoreInteractions(productService);
        }

        @Test
        @DisplayName("Should return 400 when body is invalid")
        void shouldReturn400WhenBodyIsInvalid() throws Exception {
            String invalidJson = """
                    {
                    "stock": ""
                    }
                    """;

            mockMvc.perform(
                    patch("/products/{id}/stock", 99)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(invalidJson))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(productService);
        }

        @Test
        @DisplayName("Should return 400 when dto is invalid")
        void shouldReturn400WhenDtoIsInvalid() throws Exception {
            var dto = new UpdateProductStock(-1);

            mockMvc.perform(
                    patch("/products/{id}/stock", 10)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(productService);
        }

        @Test
        @DisplayName("Should return 404 when product is not found")
        void shouldReturn404WhenProductIsNotFound() throws Exception {
            var dto = new UpdateProductStock(10);

            when(productService.updateStock(10L, dto))
                    .thenThrow(new NotFoundException("Product not found"));

            mockMvc.perform(
                    patch("/products/{id}/stock", 10)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpectAll(
                            status().isNotFound(),
                            jsonPath("$.message").value("Product not found")
                    );

            verify(productService).updateStock(10L, dto);
            verifyNoMoreInteractions(productService);
        }
    }

    @Nested
    class updateName {

        @Test
        @DisplayName("Should update product name when body is valid")
        void shouldUpdateProductNameWhenBodyIsValid() throws Exception {
            var dto = new UpdateProductName("Banana");
            var serviceResponse = new ProductDTO(
                    10L, "Banana", new CategoryDTO(10L, "Fruit"), new BigDecimal("10.0"), 10);

            when(productService.updateName(10L, dto))
                    .thenReturn(serviceResponse);

            mockMvc.perform(
                    patch("/products/{id}/name", 10)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.id").value(10),
                            jsonPath("$.name").value("Banana")
                    );

            verify(productService).updateName(10L, dto);
            verifyNoMoreInteractions(productService);
        }

        @Test
        @DisplayName("Should return 400 when body is invalid")
        void shouldReturn400WhenBodyIsInvalid() throws Exception {
            String invalidJson = """
                    {
                    "name": ""
                    }
                    """;

            mockMvc.perform(
                    patch("/products/{id}/name", 99)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(invalidJson))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(productService);
        }

        @Test
        @DisplayName("Should return 400 when dto is invalid")
        void shouldReturn400WhenDtoIsInvalid() throws Exception {
            var dto = new UpdateProductName("");

            mockMvc.perform(
                    patch("/products/{id}/name", 99)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(productService);
        }

        @Test
        @DisplayName("Should return 404 when product is not found")
        void shouldReturn404WhenProductIsNotFound() throws Exception {
            var dto = new UpdateProductName("Banana");

            when(productService.updateName(99L, dto))
                    .thenThrow(new NotFoundException("Product not found"));

            mockMvc.perform(
                    patch("/products/{id}/name", 99)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpectAll(
                            status().isNotFound(),
                            jsonPath("$.message").value("Product not found")
                    );

            verify(productService).updateName(99L, dto);
            verifyNoMoreInteractions(productService);
        }
    }

    @Nested
    class updateCategory {

        @Test
        @DisplayName("Should update product name when body is valid")
        void shouldUpdateProductNameWhenBodyIsValid() throws Exception {
            var serviceResponse = new ProductDTO(
                    10L, "Banana", new CategoryDTO(20L, "Fruit"), new BigDecimal("10.0"), 10);

            when(productService.updateCategory(10L, 20L))
                    .thenReturn(serviceResponse);

            mockMvc.perform(
                    patch("/products/{id}/category", 10)
                    .param("categoryId", "20"))
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.id").value(10),
                            jsonPath("$.category.id").value(20)
                    );

            verify(productService).updateCategory(10L, 20L);
            verifyNoMoreInteractions(productService);
        }

        @Test
        @DisplayName("Should return 404 when product is not found")
        void shouldReturn404WhenProductIsNotFound() throws Exception {
            when(productService.updateCategory(99L, 20L))
                    .thenThrow(new NotFoundException("Product not found"));

            mockMvc.perform(
                    patch("/products/{id}/category", 99)
                    .param("categoryId", "20"))
                    .andExpectAll(
                            status().isNotFound(),
                            jsonPath("$.message").value("Product not found")
                    );

            verify(productService).updateCategory(99L, 20L);
            verifyNoMoreInteractions(productService);
        }

        @Test
        @DisplayName("Should return 404 when category is not found")
        void shouldReturn404WhenCategoryIsNotFound() throws Exception {
            when(productService.updateCategory(99L, 20L))
                    .thenThrow(new NotFoundException("Category not found"));

            mockMvc.perform(
                    patch("/products/{id}/category", 99)
                    .param("categoryId", "20"))
                    .andExpectAll(
                            status().isNotFound(),
                            jsonPath("$.message").value("Category not found")
                    );

            verify(productService).updateCategory(99L, 20L);
            verifyNoMoreInteractions(productService);
        }
    }
}
