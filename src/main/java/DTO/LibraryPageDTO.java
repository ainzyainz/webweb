package DTO;

import lombok.*;

import java.util.Set;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class LibraryPageDTO {
    private int page;
    private int perPage;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<GameDTO> games;
    private int noOfPages;
}
