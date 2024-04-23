package DTO;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserDescriptionDTO {

    private long id;

    private String name;

    private String surname;

    private String address;

    private int age;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserDTO userDTO;
}
