package entities;

import lombok.*;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@ToString
@Table(name = "game_reqs")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameRequirements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "reqs")
    @Access(AccessType.PROPERTY)
    private Game game;

    @Column
    private String OS;

    @Column
    private String processor;

    @Column
    private String memory;

    @Column
    private String graphics;

    @Column
    private String directX;

    @Column
    private String storage;
}
