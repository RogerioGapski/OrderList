package com.orderList.orderList.repository;

import com.orderList.orderList.model.dto.response.ProductDTO;
import com.orderList.orderList.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    List<Product> findByCategory(String category);
}
