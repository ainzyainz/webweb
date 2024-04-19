package entities;

import lombok.*;
import utils.roles.Roles;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String password;

    @Column(name = "email")
    private String email;

    @Column
    private Roles role;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private UserDescription description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Library library;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Balance balance;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @Access(AccessType.PROPERTY)
    private Bin bin;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Review> reviews;


}
