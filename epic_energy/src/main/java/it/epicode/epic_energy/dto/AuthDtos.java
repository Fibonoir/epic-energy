package it.epicode.epic_energy.dto;

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
    public static class JwtResponse {
        private String token;
        private Long id;
        private String username;
        private String email;
        private Collection<? extends GrantedAuthority> roles;

        public JwtResponse(String token, Long id, String username, String email,
                           Collection<? extends GrantedAuthority> roles) {
            this.token = token;
            this.id = id;
            this.username = username;
            this.email = email;
            this.roles = roles;
        }
    }
}