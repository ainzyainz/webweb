package entities;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "user")
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private double balance;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


}
