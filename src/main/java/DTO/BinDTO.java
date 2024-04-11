package DTO;

import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = "userDTO")
@NoArgsConstructor
@AllArgsConstructor
public class BinDTO {
    private long id;
    private UserDTO userDTO;
    private Set<GameDTO> gameDTOSet;
}
