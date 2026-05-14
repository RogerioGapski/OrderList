package com.orderList.orderList.services;

import com.orderList.orderList.model.entities.User;
import com.orderList.orderList.model.dto.request.CreateUserDTO;
import com.orderList.orderList.model.dto.response.UserDTO;
import com.orderList.orderList.exceptions.customs.NotFoundException;
import com.orderList.orderList.utils.mapper.UserMapper;
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

    public void deleteById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userRepository.delete(user);
    }

    public void deleteByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userRepository.delete(user);
    }

    public UserDTO findById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return userMapper.toDTO(user);
    }

    public UserDTO findByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return userMapper.toDTO(user);
    }

    public UserDTO changeName(Long id, String newName){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setName(newName);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public UserDTO changeEmail(Long id, String newEmail){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setEmail(newEmail);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }


}
