package it.epicode.epic_energy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthDtos {

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Data
    public static class SignupRequest {
        private String username;
        private String email;
        private String password;
        private String role; // e.g. "admin" or null for normal user
        private String adminKey; // Only used for admin registration
    }

    @Data
    @AllArgsConstructor
    public static class JwtResponse {
        private String token;
    }
}