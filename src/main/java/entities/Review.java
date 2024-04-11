package entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
@ToString(exclude = {"game","user"})
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "review_text")
    private String reviewText;

    @ManyToOne
    @JoinColumn(name = "game_id",nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
