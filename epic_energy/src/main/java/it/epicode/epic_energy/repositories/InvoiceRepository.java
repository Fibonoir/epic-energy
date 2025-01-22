package it.epicode.epic_energy.repositories;

import it.epicode.epic_energy.models.Invoice;
import it.epicode.epic_energy.models.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Page<Invoice> findByClientId(Long clientId, Pageable pageable);

    Page<Invoice> findByStatus(InvoiceStatus status, Pageable pageable);

    Page<Invoice> findByClientIdAndStatus(Long clientId, InvoiceStatus status, Pageable pageable);

    Page<Invoice> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Invoice> findByAmountBetween(BigDecimal min, BigDecimal max, Pageable pageable);
}