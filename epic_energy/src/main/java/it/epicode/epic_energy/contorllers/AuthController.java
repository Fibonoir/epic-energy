package it.epicode.epic_energy.contorllers;

import it.epicode.epic_energy.dto.AuthDtos;
import it.epicode.epic_energy.models.User;
import it.epicode.epic_energy.repositories.RoleRepository;
import it.epicode.epic_energy.repositories.UserRepository;
import it.epicode.epic_energy.security.jwt.JwtUtils;
import it.epicode.epic_energy.security.models.UserDetailsImpl;
import it.epicode.epic_energy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<AuthDtos.JwtResponse> authenticateUser(@RequestBody AuthDtos.LoginRequest loginRequest) {
/*        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(loginRequest.getUsername());

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new AuthDtos.JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getAuthorities()));

 */      String token = userService.authenticateUser(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        );
        return ResponseEntity.ok(new AuthDtos.JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthDtos.SignupRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        User user = userService.registerUser(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                signUpRequest.getRole(),
                signUpRequest.getAdminKey());

        return ResponseEntity.ok("User registered successfully!");
    }
}