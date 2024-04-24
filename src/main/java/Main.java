import DAO.classes.UserDAOImpl;
import DAO.interfaces.UserDAO;
import DTO.UserDTO;
import entities.*;
import services.classes.GameService;
import services.classes.UserService;
import utils.roles.Roles;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        GameService gameService = new GameService();
        userService.registerUser("asd","asd@gmail.com","asd","asd");
        for (int i = 0; i < 10; i++) {
            gameService.createGame("25","game"+i,"a","a","a","a","a","a");
        }
    }

}