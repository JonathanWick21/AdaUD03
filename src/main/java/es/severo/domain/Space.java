package es.severo.domain;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "spaces")
@Data
@ToString(exclude = "bookings")
public class Space {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "code", nullable = false, length = 60)
    private String code;

    @Column(name = "hourlyPrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyPrice;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "type", nullable = false)
    private SpaceType type;

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;
    
    @OneToMany(mappedBy = "space")
    private List<Booking> bookings;

    @ManyToMany
    @JoinTable(name = "space_tag",
            joinColumns = @JoinColumn(name = "space_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    public enum SpaceType {
        MEETING_ROOM, OFFICE, STUDIO_AREA
    }

}

