package com.orderlist.api.services;

import com.orderlist.api.exceptions.customs.BadRequestException;
import com.orderlist.api.exceptions.customs.NotFoundException;
import com.orderlist.api.model.dto.response.UserRolesDTO;
import com.orderlist.api.model.entities.Role;
import com.orderlist.api.model.entities.User;
import com.orderlist.api.repository.RoleRepository;
import com.orderlist.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public UserRolesDTO addRole(UUID userId, Long roleId) {
        User user = userService.findUserById(userId);
        Role role = findRoleById(roleId);

        if(user.getRoles().contains(role))
            throw new BadRequestException("Role already exists");

        user.getRoles().add(role);
        userRepository.save(user);

        return new UserRolesDTO(user.getRoles());
    }

    @Transactional
    public UserRolesDTO removeRole(UUID userId, Long roleId) {
        User user = userService.findUserById(userId);
        Role role = findRoleById(roleId);

        if(!user.getRoles().contains(role))
            throw new BadRequestException("User doesn't have this role");

        user.getRoles().remove(role);
        userRepository.save(user);
        return new UserRolesDTO(user.getRoles());
    }

    //Auxiliary methods
    public Role findRoleById(Long roleId){
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));
    }
}
