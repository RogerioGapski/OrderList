package com.orderlist.api.controllers;

import com.orderlist.api.model.dto.request.user.UpdateEmailDTO;
import com.orderlist.api.model.dto.request.user.UpdateNameDTO;
import com.orderlist.api.model.dto.request.user.UpdatePasswordDTO;
import com.orderlist.api.model.dto.response.UserDTO;
import com.orderlist.api.security.authorization.CurrentUserId;
import com.orderlist.api.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "jwtAuth")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(userService.findByEmail(email));
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<UserDTO> updateName(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateNameDTO dto) {
        return ResponseEntity.ok().body(userService.changeName(id, dto));
    }

    @PatchMapping("/email/change")
    public ResponseEntity<UserDTO> updateEmail(
            @CurrentUserId UUID id,
            @RequestBody @Valid UpdateEmailDTO dto) {
        return ResponseEntity.ok().body(userService.changeEmail(id, dto));
    }

    @PatchMapping("/password/change")
    public ResponseEntity<Void> updatePassword(
            @CurrentUserId UUID id,
            @RequestBody @Valid UpdatePasswordDTO dto) {
        userService.changePassword(id, dto);
        return ResponseEntity.noContent().build();
    }
}
