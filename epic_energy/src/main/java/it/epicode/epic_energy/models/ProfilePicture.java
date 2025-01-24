package it.epicode.epic_energy.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "profile_picture")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfilePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] data;

    private String contentType;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
