package DTO;

import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@ToString(exclude = "userDTO")
@NoArgsConstructor
@AllArgsConstructor
public class LibraryDTO {
    private long id;
    private UserDTO userDTO;
    private Set<GameDTO> gameDTOSet;
}

