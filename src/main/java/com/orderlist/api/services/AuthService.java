package com.orderlist.api.services;

import com.orderlist.api.exceptions.customs.AlreadyExistsException;
import com.orderlist.api.model.dto.request.auth.LoginRequest;
import com.orderlist.api.model.dto.request.auth.RegisterRequest;
import com.orderlist.api.model.dto.response.LoginDTO;
import com.orderlist.api.model.dto.response.RegisterDTO;
import com.orderlist.api.model.entities.User;
import com.orderlist.api.repository.UserRepository;
import com.orderlist.api.utils.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authManager;

    @Transactional
    public RegisterDTO registerUser(RegisterRequest dto) {
        if(userRepository.existsByEmail(dto.email())){
            throw new AlreadyExistsException("Email already in use.");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String token = tokenService.generateToken(user);

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setAccessToken(token);
        registerDTO.setTokenType("Bearer");
        registerDTO.setExpiresIn(tokenService.getExpiresInSeconds());

        return registerDTO;
    }

    public LoginDTO login(LoginRequest dto) {
       Authentication auth = authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                dto.email(),
                                dto.password()
                        ));
        User user = (User) auth.getPrincipal();
        String token = tokenService.generateToken(user);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setAccessToken(token);
        loginDTO.setTokenType("Bearer");
        loginDTO.setExpiresIn(tokenService.getExpiresInSeconds());

        return loginDTO;
    }
}
