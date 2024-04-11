package DTO;

import lombok.*;

@Builder
@Getter
@Setter
@ToString(exclude = "userDTO")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserDescriptionDTO {

    private long id;

    private String name;

    private String surname;

    private String address;

    private int age;

    private UserDTO userDTO;
}
