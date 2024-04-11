package DTO;

import lombok.*;

import java.util.Set;

@Builder
@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {
    private long id;
    private String name;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private GameRequirementsDTO gameRequirementsDTO;

    @EqualsAndHashCode.Exclude
    private Set<CatalogDTO> catalogDTOSet;

    @EqualsAndHashCode.Exclude
    private Set<BinDTO> binDTOSet;

    @EqualsAndHashCode.Exclude
    private Set<LibraryDTO> libraryDTOSet;

    @EqualsAndHashCode.Exclude
    private GameStatisticsDTO gameStatisticsDTO;

    @EqualsAndHashCode.Exclude
    private Set<ReviewDTO> reviewDTOSet;
}
