package DTO;

import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CatalogDTO {
    private long id;
    private String name;
    @ToString.Exclude
    private Set<GameDTO> gameDTOS;
}
