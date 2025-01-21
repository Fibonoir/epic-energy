package it.epicode.epic_energy.dto;

import it.epicode.epic_energy.models.Address;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ClientDtos {

    private String companyName;
    private String vatNumber;
    private String companyEmail;
    private String pecEmail;
    private String companyType; // e.g. "PA", "SAS", "SPA", "SRL"

    // Contact info
    private String contactPhone;
    private String contactEmail;
    private String contactFirstName;
    private String contactLastName;
    private String contactMobile;

    // Dates and turnover
    private LocalDate insertionDate;
    private LocalDate lastContactDate;
    private BigDecimal annualTurnover;

    private Address legalAddress;

    private Address operationalAddress;

}