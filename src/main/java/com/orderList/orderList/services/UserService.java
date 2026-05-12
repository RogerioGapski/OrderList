package com.orderList.orderList.services;

import com.orderList.orderList.domain.entities.User;
import com.orderList.orderList.dto.request.CreateUserDTO;
import com.orderList.orderList.dto.response.UserDTO;
import com.orderList.orderList.exceptions.NotFoundException;
import com.orderList.orderList.mapper.UserMapper;
import com.orderList.orderList.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO createUser(CreateUserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public void deleteById(Integer id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userRepository.delete(user);
    }

    public void deleteByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userRepository.delete(user);
    }

    public UserDTO findById(Integer id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return userMapper.toDTO(user);
    }

    public UserDTO findByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return userMapper.toDTO(user);
    }

    public UserDTO updateUser(Integer id, CreateUserDTO dto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setName(dto.email());
        user.setEmail(dto.email());
        userRepository.save(user);
        return userMapper.toDTO(user);
    }
}
