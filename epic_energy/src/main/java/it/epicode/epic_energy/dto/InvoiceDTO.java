package it.epicode.epic_energy.dto;

import it.epicode.epic_energy.models.InvoiceStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InvoiceDTO {
    private Long id;
    private LocalDate date;
    private Double amount;
    private String invoiceNumber;
    private InvoiceStatus status;
    private Long clientId;
}
