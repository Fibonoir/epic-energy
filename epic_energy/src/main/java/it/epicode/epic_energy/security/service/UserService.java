package it.epicode.epic_energy.security.service;


import it.epicode.epic_energy.exceptions.ResourceNotFoundException;
import it.epicode.epic_energy.models.ERole;
import it.epicode.epic_energy.models.ProfilePicture;
import it.epicode.epic_energy.models.Role;
import it.epicode.epic_energy.models.User;
import it.epicode.epic_energy.repositories.ProfilePictureRepository;
import it.epicode.epic_energy.repositories.RoleRepository;
import it.epicode.epic_energy.repositories.UserRepository;
import it.epicode.epic_energy.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ProfilePictureRepository profilePictureRepository;

    public User registerUser(String username, String email, String rawPassword, String role, String adminKey) {

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encoder.encode(rawPassword));
        user.setAdminKey(adminKey);

        Set<Role> roles = new HashSet<>();
        if (role == null) {
            // default role: USER
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else if (role.equalsIgnoreCase("admin") && adminKey.equals("epicode24")) {
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Admin Role is not found."));
            roles.add(adminRole);
        } else {
            // default user
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: User Role is not found."));
            roles.add(userRole);
        }

        user.setRoles(roles);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String authenticateUser(String identifier, String password) {
        try {
            // Resolve username by identifier (username or email)
            String username = userRepository.findByUsername(identifier)
                    .map(User::getUsername)
                    .orElseGet(() -> userRepository.findByEmail(identifier)
                            .map(User::getUsername)
                            .orElseThrow(() -> new SecurityException("User not found with username or email: " + identifier))
                    );

            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // Generate JWT token
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtUtils.generateJwtToken(userDetails);
        } catch (AuthenticationException e) {
            throw new SecurityException("Invalid credentials", e);
        }
    }

    public void uploadProfilePicture(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        try {
            ProfilePicture profilePicture = profilePictureRepository.findByUserId(userId)
                    .orElse(new ProfilePicture());

            profilePicture.setUser(user);
            profilePicture.setContentType(file.getContentType());
            profilePicture.setData(file.getBytes());

            profilePictureRepository.save(profilePicture);
        } catch (IOException e) {
            throw new RuntimeException("Could not upload profile picture", e);
        }
    }

    public ProfilePicture getProfilePicture(Long userId) {
        return profilePictureRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No profile picture found for employee " + userId));
    }

    public void deleteProfilePicture(Long userId) {
        profilePictureRepository.findByUserId(userId).ifPresent(profilePictureRepository::delete);
    }

    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(existingUser);
    }
}