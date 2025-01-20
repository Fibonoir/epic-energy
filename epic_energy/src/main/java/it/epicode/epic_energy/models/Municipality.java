package it.epicode.epic_energy.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "municipalities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Municipality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String denominazione; // e.g., "Agliè"

    @ManyToOne
    private Province provincia; // Reference to Province
}