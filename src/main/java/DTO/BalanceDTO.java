package DTO;

import lombok.*;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDTO {
    private long id;
    private double balance;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserDTO userDTO;

}
