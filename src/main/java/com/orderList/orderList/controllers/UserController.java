package com.orderList.orderList.controllers;

import com.orderList.orderList.model.dto.request.user.UpdateEmailDTO;
import com.orderList.orderList.model.dto.request.user.UpdateNameDTO;
import com.orderList.orderList.model.dto.request.user.CreateUserDTO;
import com.orderList.orderList.model.dto.response.UserDTO;
import com.orderList.orderList.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid CreateUserDTO dto) {
        UserDTO user = userService.createUser(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.id())
                .toUri();
        return ResponseEntity.created(uri).body(user);
    }

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
        return ResponseEntity.ok().body(userService.changeName(id, dto.name()));
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<UserDTO> updateEmail(
        @PathVariable UUID id,
        @RequestBody @Valid UpdateEmailDTO dto) {
        return ResponseEntity.ok().body(userService.changeEmail(id, dto.email()));
    }
}
