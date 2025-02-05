package it.epicode.epic_energy.repositories;


import it.epicode.epic_energy.models.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, String> {
    Optional<Province> findByCodiceStorico(String codiceStorico);

    Optional<Province> findByNome(String nome);
}