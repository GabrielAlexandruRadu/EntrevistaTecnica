package com.example.core.service;

import com.example.core.DTO.LoginRequest;
import com.example.core.entity.User;
import com.example.core.repository.UserRepository;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@ApplicationScoped
public class AuthService {
    @Inject
    UserRepository userRepository;

    public String authenticate(LoginRequest request) {
        Optional<User> user = userRepository.findByUsername(request.username);
        if (user.isPresent() && user.get().password.equals(request.password)) {
            // Generate token
            return generateToken(user.get());
        }
        throw new WebApplicationException("Invalid credentials", Response.Status.UNAUTHORIZED);
    }

    private String generateToken(User user) {
        return Jwt.claims()
                .issuer("example.com")
                .subject(user.username)
                .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
                .sign();
    }
}
