package com.libraryproject.School.library.service;

import com.libraryproject.School.library.model.Role;
import com.libraryproject.School.library.model.User;
import com.libraryproject.School.library.repository.RoleRepository;
import com.libraryproject.School.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServicelmpl implements UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Usuario ya existe");
        }
        Set<Role> roles = new HashSet<>();
        if (user.getRoles() != null) {
            for (Role r : user.getRoles()) {
                Role role = roleRepository.findByName(r.getName())
                        .orElseThrow(() -> new RuntimeException("Role no encontrado: " + r.getName()));
                roles.add(role);
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        return userRepository.save(user);
    }
}


