package es.severo.Ejercicio1_2.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "players")
@Data

public class Player {


    @Id
    private Long id;

    private String name;

    private String email;

    private LocalDateTime createdAt;
}
