package com.libraryproject.School.library.auth;

import com.libraryproject.School.library.configuration.CustomUserDetailsService;
import com.libraryproject.School.library.segurity.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager,
                       CustomUserDetailsService userDetailsService,
                       JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    public AuthResponse login(AuthRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            // 🔥 EVITA QUE SWAGGER MUESTRE 500
            throw new RuntimeException("Credenciales incorrectas");
        }

        // 🔍 Se cargan los roles del usuario
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        // 🔥 Generar JWT
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }
}
