package com.orderlist.api.repository;

import com.orderlist.api.model.entities.Role;
import com.orderlist.api.model.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(Roles roleName);
}
