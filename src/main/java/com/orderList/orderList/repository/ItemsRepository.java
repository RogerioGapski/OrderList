package com.orderList.orderList.repository;

import com.orderList.orderList.model.entities.Item;
import com.orderList.orderList.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Long> {
    List<Product> addProduct(Product product);
    List<Item> findAllByUser(Long userId);
}
