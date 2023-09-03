package com.krugerChallenge.authorizationserver.service;

import com.krugerChallenge.authorizationserver.dto.MessageDto;
import com.krugerChallenge.authorizationserver.dto.UserDto;
import com.krugerChallenge.authorizationserver.entity.Role;
import com.krugerChallenge.authorizationserver.entity.User;
import com.krugerChallenge.authorizationserver.enums.RoleName;
import com.krugerChallenge.authorizationserver.repository.RoleRepository;
import com.krugerChallenge.authorizationserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public MessageDto createUser(UserDto userDto){
        User user = User.builder()
                .username(userDto.username())
                .password(passwordEncoder.encode(userDto.password()))
                .build();
        Set<Role> roles = new HashSet<>();
        userDto.roles().forEach(r -> {
            Role role = roleRepository.findByRole(RoleName.valueOf(r))
                    .orElseThrow(() -> new RuntimeException("role not found"));
            roles.add(role);
        });
        user.setRoles(roles);
        userRepository.save(user);
        return new MessageDto("user "+user.getUsername() + " saved");

    }
}
