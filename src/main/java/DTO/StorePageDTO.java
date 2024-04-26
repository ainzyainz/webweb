package DTO;


import lombok.*;

import java.util.Set;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class StorePageDTO {
    private int page;
    private int perPage;
    private int noOfPages;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CatalogDTO> catalogDTOSet;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<GameDTO> allGames;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<GameDTO> best;

}
