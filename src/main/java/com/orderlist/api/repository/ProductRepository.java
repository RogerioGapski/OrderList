package com.orderlist.api.repository;

import com.orderlist.api.model.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    Page<Product> findByCategoryName(String categoryName, Pageable pageable);
    boolean existsByCategoryId(Long categoryId);
}
