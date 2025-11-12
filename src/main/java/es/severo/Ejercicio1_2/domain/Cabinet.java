package es.severo.Ejercicio1_2.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "cabinets")
@Data
@ToString(exclude = "tags")
public class Cabinet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "build_year", nullable = false)
    private LocalDateTime buildYear;

    @Column(name = "status", nullable = false)
    private Status status;


    //Aqui como es una relación many to many al no tener datos mas alla de los ids que relacionan las tablas con hibernate no hace falta hacer la clase
    @ManyToMany
    @JoinTable(name = "cabinet_tag",
        joinColumns = @JoinColumn(name = "cabinet_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    //Esta relación porque cada maquina es especifica de un juego
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    private enum Status{
        ACTIVE, MAINTENANCE, RETIRED
    }
}
