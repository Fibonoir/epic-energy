package it.epicode.epic_energy.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Company info
    private String companyName;
    private String vatNumber;
    private String companyEmail;
    private String pecEmail;
    private CompanyType companyType; // e.g. "PA", "SAS", "SPA", "SRL"

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

    // Logo (URL or base64)
    private String companyLogo;

    // Addresses
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "legal_address_id")
    private Address legalAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "operational_address_id")
    private Address operationalAddress;
}