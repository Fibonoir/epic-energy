package it.epicode.epic_energy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false, unique = true)
    private String vatNumber;

    @Email
    @Column(nullable = false, unique = true)
    private String companyEmail;

    @Email
    @Column(nullable = false, unique = true)
    private String pecEmail;

    @Column(nullable = false)
    private String companyType; // e.g. "PA", "SAS", "SPA", "SRL"

    // Contact info
    @Column(nullable = false)
    private String contactPhone;

    @Email
    @Column(nullable = false, unique = true)
    private String contactEmail;

    @Column(nullable = false)
    private String contactFirstName;

    @Column(nullable = false)
    private String contactLastName;

    private String contactMobile;

    // Dates and turnover
    private LocalDate insertionDate;
    private LocalDate lastContactDate;

    @Column(nullable = false)
    private BigDecimal annualTurnover;

    // Logo (URL or base64)
    private String companyLogo;

    // Addresses
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "legal_address_id")
    private Address legalAddress;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "operational_address_id")
    private Address operationalAddress;
}