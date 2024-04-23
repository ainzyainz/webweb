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

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserDescriptionDTO userDescriptionDTO;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private BalanceDTO balanceDTO;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private BinDTO binDTO;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ReviewDTO> reviewDTOSet;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private LibraryDTO libraryDTO;

}
