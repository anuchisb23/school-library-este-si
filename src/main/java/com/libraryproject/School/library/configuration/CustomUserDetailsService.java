package com.libraryproject.School.library.configuration;

import com.libraryproject.School.library.model.User;
import com.libraryproject.School.library.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("🔍 Buscando usuario: " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("❌ Usuario NO encontrado en DB");
                    return new UsernameNotFoundException("Usuario no encontrado: " + username);
                });

        System.out.println("✅ Usuario encontrado: " + user.getUsername());
        System.out.println("🔐 Password en DB: " + user.getPassword());

        // LOGS DE ROLES
        String[] authorities = user.getRoles()
                .stream()
                .map(role -> {
                    System.out.println("📌 Rol encontrado: " + role.getName());
                    return role.getName();
                })
                .toArray(String[]::new);

        // LOG para ver qué authorities quedan finalmente:
        System.out.println("📌 Authorities generadas:");
        for (String a : authorities) {
            System.out.println("   → " + a);
        }

        // Construir UserDetails
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Hash BCrypt
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}

