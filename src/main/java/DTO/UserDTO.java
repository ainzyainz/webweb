package DTO;

import lombok.*;
import utils.roles.Roles;

import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private long id;

    private String password;

    private String email;

    private Roles role;

    @EqualsAndHashCode.Exclude
    private UserDescriptionDTO userDescriptionDTO;

    @EqualsAndHashCode.Exclude
    private BalanceDTO balanceDTO;

    @EqualsAndHashCode.Exclude
    private BinDTO binDTO;

    @EqualsAndHashCode.Exclude
    private Set<ReviewDTO> reviewDTOSet;

    @EqualsAndHashCode.Exclude
    private LibraryDTO libraryDTO;

}
