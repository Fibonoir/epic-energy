package it.epicode.epic_energy.repositories;


import java.math.BigDecimal;
import java.util.List;

import it.epicode.epic_energy.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Page<Client> findByCompanyNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Client> findByAnnualTurnoverBetween(BigDecimal min, BigDecimal max, Pageable pageable);
}