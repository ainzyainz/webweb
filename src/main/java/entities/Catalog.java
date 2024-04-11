package entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@EqualsAndHashCode(exclude = "games")
@Getter
@Setter
@ToString(exclude = "games")
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;


    @ManyToMany(mappedBy = "catalogs")
    private Set<Game> games = new HashSet<>();

}
