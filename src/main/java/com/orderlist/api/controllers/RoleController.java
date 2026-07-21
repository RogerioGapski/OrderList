package com.orderlist.api.controllers;

import com.orderlist.api.model.dto.response.UserRolesDTO;
import com.orderlist.api.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "jwtAuth")
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Adds a role for the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "You don't have permission to perform this action"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    @PatchMapping("/add/{userId}/{roleId}")
    public ResponseEntity<UserRolesDTO> addRole(@PathVariable UUID userId, @PathVariable Long roleId) {
        return ResponseEntity.ok().body(roleService.addRole(userId, roleId));
    }

    @Operation(summary = "Remove a role for the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "403", description = "You don't have permission to perform this action"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    @PatchMapping("/remove/{userId}/{roleId}")
    public ResponseEntity<UserRolesDTO> removeRole(@PathVariable UUID userId, @PathVariable Long roleId) {
        return ResponseEntity.ok().body(roleService.removeRole(userId, roleId));
    }
}
