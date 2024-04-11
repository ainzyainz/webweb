package DTO;

import lombok.*;

@Builder
@ToString(exclude = "gameDTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewDTO {
    private long id;
    private String reviewText;
    private GameDTO gameDTO;
    private UserDTO userDTO;
}
