package it.epicode.epic_energy.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "provinces")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Province {

    @Id
    private String code; // e.g., "AG"

    @Column(nullable = false)
    private String name; // e.g., "Agrigento"

    @Column(nullable = false)
    private String region; // e.g., "Sicilia"
}