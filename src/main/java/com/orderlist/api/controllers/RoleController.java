package com.orderlist.api.controllers;

import com.orderlist.api.model.dto.response.UserRolesDTO;
import com.orderlist.api.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @PatchMapping("/add/{userId}/{roleId}")
    public ResponseEntity<UserRolesDTO> addRole(@PathVariable UUID userId, @PathVariable Long roleId) {
        return ResponseEntity.ok().body(roleService.addRole(userId, roleId));
    }

    @PatchMapping("/remove/{userId}/{roleId}")
    public ResponseEntity<UserRolesDTO> removeRole(@PathVariable UUID userId, @PathVariable Long roleId) {
        return ResponseEntity.ok().body(roleService.removeRole(userId, roleId));
    }
}
