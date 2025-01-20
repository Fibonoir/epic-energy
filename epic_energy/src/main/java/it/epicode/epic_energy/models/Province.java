package it.epicode.epic_energy.models;

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
    private String sigla; // e.g., "AG"

    private String nome; // e.g., "Agrigento"

    private String regione; // e.g., "Sicilia"

    private String codiceStorico; // e.g., "001"
}
