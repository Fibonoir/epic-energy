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
    @Column(name = "profile_picture", nullable = true)
    private byte[] data;

    @Column(name = "content_type", nullable = true)
    private String contentType;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
