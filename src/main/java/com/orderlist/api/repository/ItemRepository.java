package com.orderlist.api.repository;

import com.orderlist.api.model.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAllByUserId(UUID userId, Pageable pageable);
    @Query("SELECT i FROM Item i WHERE i.user.id = :userId")
    List<Item> findAllByUserIdNoPageable(UUID userId);
}
