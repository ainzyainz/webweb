package entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@ToString
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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Game> games = new HashSet<>();

}
