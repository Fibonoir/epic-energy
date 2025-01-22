package it.epicode.epic_energy.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ClientDTO {
    private Long id;

    // Company Info
    private String companyName;
    private String vatNumber;
    private String companyEmail;
    private String pecEmail;
    private String companyType;

    // Contact Info
    private String contactEmail;
    private String contactFirstName;
    private String contactLastName;
    private String contactPhone;
    private String contactMobile;

    // Dates and Financials
    private LocalDate insertionDate;
    private LocalDate lastContactDate;
    private BigDecimal annualTurnover;

    // Logo
    private String companyLogo;

    // Addresses
    private AddressDTO legalAddress;
    private AddressDTO operationalAddress;
}
