package com.orderlist.api.services;

import com.orderlist.api.exceptions.customs.AlreadyExistsException;
import com.orderlist.api.model.dto.request.user.UpdateEmailDTO;
import com.orderlist.api.model.dto.request.user.UpdateNameDTO;
import com.orderlist.api.model.dto.request.user.UpdatePasswordDTO;
import com.orderlist.api.model.entities.User;
import com.orderlist.api.model.dto.response.UserDTO;
import com.orderlist.api.exceptions.customs.NotFoundException;
import com.orderlist.api.utils.mapper.UserMapper;
import com.orderlist.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    public void deleteById(UUID id){
        User user = findUserById(id);
        userRepository.delete(user);
    }

    public UserDTO findById(UUID id){
        return userMapper.toDTO(findUserById(id));
    }

    public UserDTO findByEmail(String email){
        return userMapper.toDTO(findUserByEmail(email));
    }

    @Transactional
    public UserDTO changeName(UUID id, UpdateNameDTO newName){
        User user = findUserById(id);
        user.setName(newName.name());
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO changeEmail(UUID id, UpdateEmailDTO newEmail) {
        if(userRepository.existsByEmail(newEmail.email())){
            throw new AlreadyExistsException("Email already in use.");
        }
        User user = findUserById(id);
        user.setEmail(newEmail.email());
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    public void changePassword(UUID id, UpdatePasswordDTO newPassword) {
        User user = findUserById(id);
        user.setPassword(passwordEncoder.encode(newPassword.password()));
        userRepository.save(user);
    }

    //Auxiliary method
    public User findUserById(UUID id){
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User findUserByEmail(String email){
        return  userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
