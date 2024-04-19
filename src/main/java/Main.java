import DAO.classes.UserDAOImpl;
import DAO.interfaces.UserDAO;
import DTO.UserDTO;
import entities.*;
import services.classes.UserService;
import utils.roles.Roles;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        userService.registerUser("asd","asd@gmail.com","asd","asd");
        Set<UserDTO> userSet = userService.searchUser("asd");
        System.out.println(userSet);
    }

}