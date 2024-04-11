package entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@ToString
@Table
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;



    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_recs_id")
    private GameRequirements reqs;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_stats_id")
    private GameStatistics stats;

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "game_catalog",
            joinColumns = { @JoinColumn(name = "game_id") },
            inverseJoinColumns = { @JoinColumn(name = "catalog_id") }
    )
    @EqualsAndHashCode.Exclude
    private Set<Catalog> catalogs = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.REMOVE})
    @JoinTable(
            name = "game_bin",
            joinColumns = { @JoinColumn(name = "game_id") },
            inverseJoinColumns = { @JoinColumn(name = "bin_id") }
    )
    @EqualsAndHashCode.Exclude
    private Set<Bin> bins = new HashSet<>();

    @OneToMany(mappedBy = "game")
    @EqualsAndHashCode.Exclude
    private Set<Review> reviews;


    @ManyToMany(cascade = { CascadeType.REMOVE })
    @JoinTable(
            name = "game_library",
            joinColumns = { @JoinColumn(name = "game_id") },
            inverseJoinColumns = { @JoinColumn(name = "library_id") }
    )
    @EqualsAndHashCode.Exclude
    private Set<Library> libraries = new HashSet<>();

}
