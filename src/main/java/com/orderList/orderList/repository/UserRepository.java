package com.orderList.orderList.repository;

import com.orderList.orderList.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
