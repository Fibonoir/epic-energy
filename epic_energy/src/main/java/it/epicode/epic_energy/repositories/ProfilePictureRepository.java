package it.epicode.epic_energy.repositories;

import it.epicode.epic_energy.models.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Long> {
    Optional<ProfilePicture> findByUserId(Long userId);
}
