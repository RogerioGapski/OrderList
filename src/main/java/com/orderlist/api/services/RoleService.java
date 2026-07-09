package com.orderlist.api.services;

import com.orderlist.api.exceptions.customs.BadRequestException;
import com.orderlist.api.exceptions.customs.NotFoundException;
import com.orderlist.api.model.dto.response.UserRolesDTO;
import com.orderlist.api.model.entities.Role;
import com.orderlist.api.model.entities.User;
import com.orderlist.api.repository.RoleRepository;
import com.orderlist.api.repository.UserRepository;
import com.orderlist.api.utils.mapper.RoleMapper;
import com.orderlist.api.utils.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public UserRolesDTO addRole(UUID userId, Long roleId) {
        User user = userService.findUserById(userId);
        Role role = findRoleById(roleId);

        if(user.getRoles().contains(role))
            throw new BadRequestException("Role already exists");

        user.getRoles().add(role);
        userRepository.save(user);

        return toUserRolesDTO(user);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public UserRolesDTO removeRole(UUID userId, Long roleId) {
        User user = userService.findUserById(userId);
        Role role = findRoleById(roleId);

        if(!user.getRoles().contains(role))
            throw new BadRequestException("User doesn't have this role");

        user.getRoles().remove(role);
        userRepository.save(user);
        return toUserRolesDTO(user);
    }

    //Auxiliary methods
    Role findRoleById(Long roleId){
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));
    }

    private UserRolesDTO toUserRolesDTO(User user) {
        return new UserRolesDTO(userMapper.toDTO(user),
                user.getRoles().stream()
                        .map(roleMapper::toDTO)
                        .collect(Collectors.toSet()));
    }
}
