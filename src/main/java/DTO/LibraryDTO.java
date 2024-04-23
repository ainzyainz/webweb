package DTO;

import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LibraryDTO {
    private long id;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UserDTO userDTO;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<GameDTO> gameDTOSet;
}

