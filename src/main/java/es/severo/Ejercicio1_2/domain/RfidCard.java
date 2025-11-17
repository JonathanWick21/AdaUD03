package es.severo.Ejercicio1_2.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "Rfid_Card")
@Data
public class RfidCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid", nullable = false, unique = true, length = 50)
    private String uid;

    @Column(name = "issued_at", nullable = false)
    private String issuedAt;

    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToOne
    @JoinColumn(name = "player_id")
    private Player player;

}
