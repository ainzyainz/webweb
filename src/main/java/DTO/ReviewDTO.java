package DTO;

import lombok.*;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewDTO {
    private long id;
    private String reviewText;
    @ToString.Exclude
    private GameDTO gameDTO;
    @ToString.Exclude
    private UserDTO userDTO;
}
