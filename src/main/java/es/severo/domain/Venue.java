package es.severo.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NamedQuery;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "venues")
@Getter
@Setter
@NoArgsConstructor
@NamedQuery(
        name = "Venue.findByName",
        query = "select v from Venue v where v.name = :name order by v.name asc"
)
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address", nullable = false, length = 250)
    private String address;

    @Column(name = "city", nullable = false,length = 120)
    private String city;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "venue")
    private List<Space> space;
    
    @ManyToMany
    @JoinTable(name = "space_tag",
        joinColumns = @JoinColumn(name = "space_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;
}
