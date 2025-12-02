package com.example.inmemoryeventsapi.infraestructura.adapters.in.web;

import com.example.inmemoryeventsapi.infraestructura.adapters.in.web.dto.AuthenticationRequest;
import com.example.inmemoryeventsapi.infraestructura.adapters.in.web.dto.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenAccessProtectedEndpointWithoutToken_thenForbidden() throws Exception {
        mockMvc.perform(get("/events"))
                .andExpect(status().isForbidden());
    }

    @Test
    void whenRegisterAndLogin_thenSuccess() throws Exception {
        // Register
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

        // Login
        AuthenticationRequest loginRequest = AuthenticationRequest.builder()
                .email("john.doe@example.com")
                .password("password123")
                .build();

        String tokenResponse = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn().getResponse().getContentAsString();

        String token = objectMapper.readTree(tokenResponse).get("token").asText();

        // Access Protected Endpoint
        mockMvc.perform(get("/events")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Autowired
    private com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.repository.UserJpaRepository userRepository;
    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
    @Autowired
    private com.example.inmemoryeventsapi.infraestructura.adapters.in.web.security.JwtService jwtService;

    @Test
    void whenAdminAccessDeleteEndpoint_thenSuccess() throws Exception {
        // Create Admin User
        var admin = com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.UserEntity.builder()
                .firstname("Admin")
                .lastname("User")
                .email("admin@example.com")
                .password(passwordEncoder.encode("admin123"))
                .role(com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.Role.ADMIN)
                .build();
        userRepository.save(admin);
        String token = jwtService.generateToken(admin);

        // Try to delete (assuming event 1 exists or at least we get 404/204, not 403)
        // Since we don't have event 1, it might return 404, but that means auth passed.
        // If 403, auth failed.
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/events/999")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound()); // 404 means authorized but resource missing
    }

    @Test
    void whenUserAccessDeleteEndpoint_thenForbidden() throws Exception {
        // Create Regular User
        var user = com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.UserEntity.builder()
                .firstname("User")
                .lastname("Regular")
                .email("user.regular@example.com")
                .password(passwordEncoder.encode("user123"))
                .role(com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.Role.USER)
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/events/999")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }
}
