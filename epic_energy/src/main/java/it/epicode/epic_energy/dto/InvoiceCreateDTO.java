package it.epicode.epic_energy.dto;

import it.epicode.epic_energy.models.InvoiceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InvoiceCreateDTO {

    @NotNull
    private LocalDate date;

    @NotNull
    private Double amount;

    @NotBlank
    private String invoiceNumber;

    @NotNull
    private InvoiceStatus status;

    @NotNull
    private Long clientId;
}
