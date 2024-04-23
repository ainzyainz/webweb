package DTO;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameStatisticsDTO {
    private long id;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private GameDTO gameDTO;
    private long purchaseCounter;
    private double price;
}
