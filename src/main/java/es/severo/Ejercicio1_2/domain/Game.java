package es.severo.Ejercicio1_2.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "games")
@Data
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "release_year", nullable = false)
    private LocalDateTime releaseYear;


    @OneToMany(mappedBy = "game")
    private List<Cabinet> cabinets;
}
