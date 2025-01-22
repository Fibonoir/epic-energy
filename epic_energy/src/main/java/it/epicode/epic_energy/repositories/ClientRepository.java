package it.epicode.epic_energy.repositories;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.epicode.epic_energy.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Page<Client> findAllByOrderByCompanyNameAsc(Pageable pageable);
    Page<Client> findAllByOrderByAnnualTurnoverAsc(Pageable pageable);
    Page<Client> findAllByOrderByContactFirstNameAsc(Pageable pageable);
    Page<Client> findAllByOrderByInsertionDateAsc(Pageable pageable);
    Page<Client> findAllByOrderByLastContactDateAsc(Pageable pageable);
    Page<Client> findAllByOrderByLegalAddressMunicipalityProvinceNameAsc(Pageable pageable);

    Page<Client> findByCompanyNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Client> findByAnnualTurnoverGreaterThanEqual(BigDecimal annualTurnover, Pageable pageable);
    Page<Client> findByAnnualTurnoverLessThanEqual(BigDecimal annualTurnover, Pageable pageable);
    Page<Client> findByAnnualTurnoverBetween(BigDecimal min, BigDecimal max, Pageable pageable);
    Page<Client> findByContactFirstNameContainingIgnoreCaseOrContactLastNameContainingIgnoreCase(String firstName, String lastName, Pageable pageable);    Page<Client> findByInsertionDateBetween(Date start, Date end, Pageable pageable);
    Page<Client> findByLastContactDateBetween(Date start, Date end, Pageable pageable);

}