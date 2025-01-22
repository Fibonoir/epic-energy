package it.epicode.epic_energy.service;


import it.epicode.epic_energy.dto.InvoiceCreateDTO;
import it.epicode.epic_energy.dto.InvoiceDTO;
import it.epicode.epic_energy.dto.InvoiceUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface InvoiceService {
    Page<InvoiceDTO> filterInvoices(            Long clientId,
                                                String status,
                                                LocalDate startDate,
                                                LocalDate endDate,
                                                BigDecimal minAmount,
                                                BigDecimal maxAmount,
                                                Integer year,
                                                Pageable pageable);
    InvoiceDTO createInvoice(InvoiceCreateDTO invoiceCreateDTO);
    InvoiceDTO getInvoiceById(Long id);
    InvoiceDTO updateInvoice(Long id, InvoiceUpdateDTO invoiceUpdateDTO);
    void deleteInvoice(Long id);

}