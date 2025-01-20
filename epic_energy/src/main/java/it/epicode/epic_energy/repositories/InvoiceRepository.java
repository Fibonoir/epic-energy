package it.epicode.epic_energy.repositories;

import it.epicode.epic_energy.models.Invoice;
import it.epicode.epic_energy.models.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Page<Invoice> findByStatus(InvoiceStatus status, Pageable pageable);
    // etc.
}