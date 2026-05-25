package com.orderList.orderList.repository;

import com.orderList.orderList.model.entities.Items;
import com.orderList.orderList.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    void addItems(List<Items> items);
}
