package com.orderlist.api.services;

import com.orderlist.api.exceptions.customs.AlreadyExistsException;
import com.orderlist.api.exceptions.customs.NotFoundException;
import com.orderlist.api.model.dto.request.auth.LoginRequest;
import com.orderlist.api.model.dto.request.auth.RegisterRequest;
import com.orderlist.api.model.dto.response.LoginDTO;
import com.orderlist.api.model.dto.response.RegisterDTO;
import com.orderlist.api.model.entities.Role;
import com.orderlist.api.model.enums.Roles;
import com.orderlist.api.model.entities.User;
import com.orderlist.api.repository.RoleRepository;
import com.orderlist.api.repository.UserRepository;
import com.orderlist.api.security.autentication.CustomUserDetails;
import com.orderlist.api.security.autentication.JwtService;
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
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final RoleRepository roleRepository;

    @Transactional
    public RegisterDTO registerUser(RegisterRequest dto) {
        if(userRepository.existsByEmail(dto.email())){
            throw new AlreadyExistsException("Email already in use.");
        }

        Role defaultPermission = roleRepository.findByName(Roles.ROLE_USER)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(defaultPermission);
        userRepository.save(user);
        String token = jwtService.generateToken(user);

        return new RegisterDTO(token, "Bearer", jwtService.getExpiresInSeconds(), userMapper.toDTO(user));
    }

    public LoginDTO login(LoginRequest dto) {
       Authentication auth = authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                dto.email(),
                                dto.password()
                        ));
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(user.getUser());

        return new LoginDTO(user.getUser().getId(), token, "Bearer", jwtService.getExpiresInSeconds());
    }
}
