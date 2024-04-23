package DTO;

import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BinDTO {
    private long id;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserDTO userDTO;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<GameDTO> gameDTOSet;
}
