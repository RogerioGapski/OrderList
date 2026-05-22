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

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(
            @RequestBody @Valid CreateUserDTO userDTO
    ){
        UserDTO user = userService.createUser(userDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.id())
                .toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long userId
    ){
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deleteByEmail(
            @PathVariable String email
    ){
        userService.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(
            @PathVariable Long id
    ){
        UserDTO dto = userService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> findByEmail(
            @PathVariable String email
    ){
        UserDTO dto = userService.findByEmail(email);
        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateName(
            @PathVariable Long id,
            @RequestBody @Valid UpdateNameDTO changeName
    ){
        UserDTO dto = userService.changeName(id, changeName.name());
        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/email/{email}")
    public ResponseEntity<UserDTO> updateEmail(
        @PathVariable String email,
        @RequestBody @Valid UpdateEmailDTO changeEmail
    ){
        UserDTO dto = userService.changeEmail(email, changeEmail.email());
        return ResponseEntity.ok().body(dto);
    }
}
