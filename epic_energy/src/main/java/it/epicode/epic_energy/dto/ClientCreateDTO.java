package it.epicode.epic_energy.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ClientCreateDTO {
    // Company Info
    @NotBlank
    private String companyName;

    @NotBlank
    private String vatNumber;

    @Email
    @NotBlank
    private String companyEmail;

    @Email
    private String pecEmail;

    @NotBlank
    private String companyType; // e.g., "PA", "SAS", "SPA", "SRL"

    // Contact Info
    @NotBlank
    private String contactEmail;

    @NotBlank
    private String contactFirstName;

    @NotBlank
    private String contactLastName;

    @NotBlank
    private String contactPhone;

    private String contactMobile;

    // Dates and Financials
    @NotNull
    private LocalDate insertionDate;

    private LocalDate lastContactDate;

    @DecimalMin("0.0")
    private BigDecimal annualTurnover;

    // Logo
    private String companyLogo; // URL or base64

    // Addresses
    @NotNull
    private AddressDTO legalAddress;

    private AddressDTO operationalAddress;
}
