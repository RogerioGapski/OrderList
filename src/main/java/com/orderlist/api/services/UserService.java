package com.orderlist.api.services;

import com.orderlist.api.exceptions.customs.AlreadyExistsException;
import com.orderlist.api.model.entities.User;
import com.orderlist.api.model.dto.request.user.CreateUserDTO;
import com.orderlist.api.model.dto.response.UserDTO;
import com.orderlist.api.exceptions.customs.NotFoundException;
import com.orderlist.api.utils.mapper.UserMapper;
import com.orderlist.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDTO createUser(CreateUserDTO dto) {
        if(userRepository.existsByEmail(dto.email())){
            throw new AlreadyExistsException("Email already in use.");
        }

        User user = userMapper.toEntity(dto);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

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
    public UserDTO changeName(UUID id, String newName){
        User user = findUserById(id);
        user.setName(newName);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO changeEmail(UUID id, String newEmail) {
        if(userRepository.existsByEmail(newEmail)){
            throw new AlreadyExistsException("Email already in use.");
        }
        User user = findUserById(id);
        user.setEmail(newEmail);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public User findUserById(UUID id){
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User findUserByEmail(String email){
        return  userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
