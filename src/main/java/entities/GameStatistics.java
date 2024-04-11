package entities;

import lombok.*;

import javax.persistence.*;

@Entity
@EqualsAndHashCode(exclude = "game")
@ToString
@Getter
@Setter
@Table(name = "game_stats")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "stats")
    @Access(AccessType.PROPERTY)
    private Game game;

    @Column
    private double price;

    @Column(name = "purchase_counter")
    private long purchaseCounter;
}
