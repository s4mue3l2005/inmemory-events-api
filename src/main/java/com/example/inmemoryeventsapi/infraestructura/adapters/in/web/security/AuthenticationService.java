package com.example.inmemoryeventsapi.infraestructura.adapters.in.web.security;

import com.example.inmemoryeventsapi.infraestructura.adapters.in.web.dto.AuthenticationRequest;
import com.example.inmemoryeventsapi.infraestructura.adapters.in.web.dto.AuthenticationResponse;
import com.example.inmemoryeventsapi.infraestructura.adapters.in.web.dto.RegisterRequest;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.Role;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.UserEntity;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserJpaRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
