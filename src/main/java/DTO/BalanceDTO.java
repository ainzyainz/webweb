package DTO;

import lombok.*;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

@Builder
@Getter
@Setter
@ToString(exclude = "userDTO")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDTO {
    private long id;
    private double balance;
    private UserDTO userDTO;

}
