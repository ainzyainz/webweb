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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CatalogDTO> catalogDTOSet;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<BinDTO> binDTOSet;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<LibraryDTO> libraryDTOSet;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private GameStatisticsDTO gameStatisticsDTO;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ReviewDTO> reviewDTOSet;
}
