package it.epicode.epic_energy.repositories;

import it.epicode.epic_energy.models.ERole;
import it.epicode.epic_energy.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}