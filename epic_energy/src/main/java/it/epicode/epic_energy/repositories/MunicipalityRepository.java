package it.epicode.epic_energy.repositories;

import it.epicode.epic_energy.models.Municipality;
import it.epicode.epic_energy.models.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {
    Optional<Municipality> findByDenominazioneAndProvincia(String denominazione, Province provincia);

    boolean existsByDenominazioneAndProvincia(String denominazione, Province provincia);
}