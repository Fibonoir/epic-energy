package it.epicode.epic_energy.security.service;

import  it.epicode.epic_energy.models.Role;
import it.epicode.epic_energy.models.User;
import it.epicode.epic_energy.repositories.UserRepository;
import it.epicode.epic_energy.security.models.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(identifier)
                .orElseGet(() -> userRepository.findByEmail(identifier)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + identifier))
                );
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(Role::getName)
                        .map(Enum::name)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet())
        );
    }
}