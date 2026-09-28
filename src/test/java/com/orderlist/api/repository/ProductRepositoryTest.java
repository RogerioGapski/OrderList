package com.orderlist.api.repository;

import com.orderlist.api.config.TestcontainersConfig;
import com.orderlist.api.model.entities.Category;
import com.orderlist.api.model.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfig.class)
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Nested
    class findByName {

        @Test
        @DisplayName("Should return product when name is valid")
        void shouldReturnProductWhenNameIsValid() {
            var category = new Category(null, "Fruit", List.of());

            entityManager.persist(category);

            entityManager.persist(
                    new Product(null, "Banana", new BigDecimal("10.0"), 10, null, category));

            entityManager.persist(
                    new Product(null, "Maça", new BigDecimal("10.0"), 10, null, category));

            entityManager.flush();
            entityManager.clear();

            Optional<Product> product1 = productRepository.findByName("Banana");
            Optional<Product> product2 = productRepository.findByName("Maça");

            assertThat(product1.get().getName())
                    .isEqualTo("Banana");

            assertThat(product2.get().getName())
                    .isEqualTo("Maça");
        }

        @Test
        @DisplayName("Should return empty when product is not found")
        void shouldReturnEmptyWhenProductIsNotFound() {
            assertThat(productRepository.findByName("Empty")).isEmpty();
        }
    }

    @Nested
    class findByCategoryName {

        @Test
        @DisplayName("Should return product when category exists")
        void shouldReturnProductWhenCategoryExists() {
            var category = new Category(null, "Fruit", List.of());
            var category2 = new Category(null, "Books", List.of());

            var product1 = new Product(
                    null, "Banana", new BigDecimal("4.99"), 10, null, category);

            var product2 = new Product(
                    null, "Maça", new BigDecimal("3.50"), 10, null, category);

            var product3 = new Product(
                    null, "Book", new BigDecimal("10.00"), 10, null, category2);

            entityManager.persist(category);
            entityManager.persist(category2);
            entityManager.persist(product1);
            entityManager.persist(product2);
            entityManager.persist(product3);
            entityManager.flush();
            entityManager.clear();

            Pageable page = PageRequest.of(0, 3);
            Page<Product> products = productRepository.findByCategoryName("Fruit", page);

            assertThat(products.getTotalElements()).isEqualTo(2);

            assertThat(products.getContent().get(0).getName())
                    .isEqualTo("Banana");

            assertThat(products.getContent().get(1).getName())
                    .isEqualTo("Maça");
        }

        @Test
        @DisplayName("Should return empty when there is no product linked to category")
        void shouldReturnEmptyWhenThereIsNoProductLinkedToCategory() {
            var category = new Category(null, "Fruit", List.of());

            entityManager.persistAndFlush(category);

            Pageable page = PageRequest.of(0, 2);
            Page<Product> products = productRepository.findByCategoryName("Fruit", page);

            assertThat(products.getTotalElements()).isEqualTo(0);
        }
    }

    @Nested
    class existsByCategoryId {

        @Test
        @DisplayName("Should return true when there is product linked to category")
        void shouldReturnTrueWhenThereIsProductLinkedToCategory() {
            var category = new Category(null, "Fruit", List.of());
            var product = new Product(
                    null, "Banana", new BigDecimal("4.00"), 10, null, category);

            entityManager.persist(category);
            entityManager.persist(product);
            entityManager.flush();
            entityManager.clear();

            assertThat(productRepository.existsByCategoryId(
                    category.getId())).isTrue();
        }

        @Test
        @DisplayName("Should return false when there is no product linked to category")
        void shouldReturnFalseWhenThereIsNoProductLinkedToCategory() {
            var category = new Category(null, "Fruit", List.of());

            entityManager.persistAndFlush(category);

            assertThat(productRepository.existsByCategoryId(category.getId()))
                    .isFalse();
        }

    }
}