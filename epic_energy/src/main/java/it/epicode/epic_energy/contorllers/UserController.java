package it.epicode.epic_energy.contorllers;

import it.epicode.epic_energy.models.ProfilePicture;
import it.epicode.epic_energy.models.User;
import it.epicode.epic_energy.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping(value = "/{id}/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadProfilePicture(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        userService.uploadProfilePicture(id, file);
        return ResponseEntity.ok( ).build();
    }

    @GetMapping("/{id}/profile-picture")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable Long id) {
        ProfilePicture imageData = userService.getProfilePicture(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imageData.getContentType()))
                .body(imageData.getData());
    }

    @DeleteMapping("/{id}/profile-picture")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfilePicture(@PathVariable Long id) {
        userService.deleteProfilePicture(id);
    }
}