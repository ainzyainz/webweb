package DTO;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GameRequirementsDTO {

    private long id;
    private String OS;
    private String memory;
    private String graphics;
    private String directX;
    private String storage;
    private String processor;
    @ToString.Exclude
    private GameDTO gameDTO;
}
