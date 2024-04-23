package utils.converter;

import DTO.*;
import entities.*;

import java.util.Set;
import java.util.stream.Collectors;

public class UserConverter {
    private LibraryConverter libraryConverter = new LibraryConverter();
    private BinConverter binConverter = new BinConverter();

    private BalanceConverter balanceConverter = new BalanceConverter();
    private UserDescConverter userDescConverter = new UserDescConverter();

    public User apply(UserDTO userDTO) {
        User user = User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .build();

        Library library = libraryConverter.apply(userDTO.getLibraryDTO());
        library.setUser(user);
        user.setLibrary(library);

        Bin bin = binConverter.apply(userDTO.getBinDTO());
        bin.setUser(user);
        user.setBin(bin);

        Balance balance = balanceConverter.apply(userDTO.getBalanceDTO());
        balance.setUser(user);
        user.setBalance(balance);


        UserDescription userDescription = userDescConverter.apply(userDTO.getUserDescriptionDTO());
        userDescription.setUser(user);
        user.setDescription(userDescription);

        return user;
    }
    public UserDTO applyDTO(User user){
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .password(user.getPassword())
                .build();



        LibraryDTO libraryDTO = libraryConverter.applyDTO(user.getLibrary());
        libraryDTO.setUserDTO(userDTO);
        userDTO.setLibraryDTO(libraryDTO);

        BinDTO binDTO = binConverter.applyDTO(user.getBin());
        binDTO.setUserDTO(userDTO);
        userDTO.setBinDTO(binDTO);

        BalanceDTO balanceDTO = balanceConverter.applyDTO(user.getBalance());
        balanceDTO.setUserDTO(userDTO);
        userDTO.setBalanceDTO(balanceDTO);

        UserDescriptionDTO userDescriptionDTO = userDescConverter.applyDTO(user.getDescription());
        userDescriptionDTO.setUserDTO(userDTO);
        userDTO.setUserDescriptionDTO(userDescriptionDTO);

        return userDTO;
    }
}
