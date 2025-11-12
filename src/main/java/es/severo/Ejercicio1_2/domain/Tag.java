package es.severo.Ejercicio1_2.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity(name = "tags")
@Data
@ToString(exclude = "cabinets")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "cabinets")
    private List<Cabinet> cabinets;
}
