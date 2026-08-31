package com.orderlist.api.services;

import com.orderlist.api.exceptions.customs.AlreadyExistsException;
import com.orderlist.api.exceptions.customs.InvalidCredentialsException;
import com.orderlist.api.model.dto.request.user.OldPasswordDTO;
import com.orderlist.api.model.dto.request.user.UpdateEmailDTO;
import com.orderlist.api.model.dto.request.user.UpdateNameDTO;
import com.orderlist.api.model.dto.request.user.UpdatePasswordDTO;
import com.orderlist.api.model.entities.User;
import com.orderlist.api.model.dto.response.UserDTO;
import com.orderlist.api.exceptions.customs.NotFoundException;
import com.orderlist.api.utils.mapper.UserMapper;
import com.orderlist.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.user.id")
    public void deleteById(UUID id){
        User user = findUserById(id);
        userRepository.delete(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO findById(UUID id){
        return userMapper.toDTO(findUserById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO findByEmail(String email){
        return userMapper.toDTO(findUserByEmail(email));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.user.id")
    public UserDTO changeName(UUID id, UpdateNameDTO newName){
        User user = findUserById(id);
        user.setName(newName.name());
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    @PreAuthorize("#id == authentication.principal.user.id")
    public UserDTO changeEmail(UUID id, UpdateEmailDTO newEmail) {
        if(userRepository.existsByEmail(newEmail.email())){
            throw new AlreadyExistsException("Email already in use");
        }
        User user = findUserById(id);
        user.setEmail(newEmail.email());
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    @PreAuthorize("#id == authentication.principal.user.id")
    public void changePassword(UUID id, OldPasswordDTO oldPassword, UpdatePasswordDTO newPassword) {
        User user = findUserById(id);
        boolean passwordCorrect = passwordEncoder.matches(oldPassword.password(), user.getPassword());

        if(passwordCorrect){
            user.setPassword(passwordEncoder.encode(newPassword.password()));
            userRepository.save(user);
        } else {
            throw new InvalidCredentialsException("Invalid current password");
        }
    }

    //Auxiliary method
    User findUserById(UUID id){
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    User findUserByEmail(String email){
        return  userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
