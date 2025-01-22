package it.epicode.epic_energy.repositories;

import it.epicode.epic_energy.models.Municipality;
import it.epicode.epic_energy.models.Province;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {
    Optional<Municipality> findByName(String name);
    Optional<Object> findByNameAndProvince_Code(String nome, String provinceCode);
}