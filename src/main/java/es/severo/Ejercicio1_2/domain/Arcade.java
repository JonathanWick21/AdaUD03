package es.severo.Ejercicio1_2.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "arcades")
@Data
public class Arcade {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;


    @OneToMany(mappedBy = "arcade")
    private List<Player> players;
}

