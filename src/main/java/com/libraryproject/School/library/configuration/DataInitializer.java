package com.libraryproject.School.library.configuration;

import com.libraryproject.School.library.model.Role;
import com.libraryproject.School.library.model.User;
import com.libraryproject.School.library.repository.RoleRepository;
import com.libraryproject.School.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // ===========================
        // CREAR ROLES SI NO EXISTEN
        // ===========================
        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));

        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));

        // ===========================
        // CREAR USUARIO userOne
        // ===========================
        if (!userRepository.existsByUsername("userOne")) {

            User userOne = User.builder()
                    .username("userOne")
                    .password(passwordEncoder.encode("laila23"))
                    .roles(Set.of(roleUser))
                    .build();

            userRepository.save(userOne);
            System.out.println("Usuario userOne creado");
        }

        // ===========================
        // CREAR USUARIO admin
        // ===========================
        if (!userRepository.existsByUsername("admin")) {

            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("laila23"))
                    .roles(Set.of(roleAdmin))
                    .build();

            userRepository.save(admin);
            System.out.println("Usuario admin creado");
        }

        System.out.println("Datos iniciales cargados.");
    }
}
