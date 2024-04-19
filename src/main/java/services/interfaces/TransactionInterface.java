package services.interfaces;

import DTO.UserDTO;
import entities.User;

public interface TransactionInterface {
    void fromBalance(User user, double num);
    void toBalance(UserDTO userDTO, double num);
    boolean buyAllFromBin(UserDTO userDTO);
    boolean buyGame(String game_id,UserDTO userDTO);
}
