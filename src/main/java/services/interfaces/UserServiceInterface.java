package services.interfaces;

import DTO.GameDTO;
import DTO.UserDTO;

import java.util.Set;

public interface UserServiceInterface {
    boolean writeReview(String text, UserDTO userDTO, GameDTO gameDTO);
    UserDTO loginUser(String email, String password);
    Set<UserDTO> getAllUsers();
    boolean removeFromBin(String game_id, UserDTO userDTO);
    boolean addToBin(String game_id, UserDTO userDTO);
    UserDTO registerUser(String password, String email,
                         String name, String surname);
    Set<UserDTO> searchUser(String search);
    boolean deleteUser(String user_id);
    UserDTO updateUser(String user_id, String name,
                       String surname, String address,
                       String user_age);

}
