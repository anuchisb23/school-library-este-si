package com.libraryproject.School.library.model;

import org.springframework.security.core.GrantedAuthority;  // Importante para usar GrantedAuthority
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role implements GrantedAuthority {  // Implementamos GrantedAuthority

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @Override
    public String getAuthority() {
        return name;  // Devolvemos el nombre del rol como la autoridad
    }
}

