import DAO.classes.CatalogDAOImpl;
import DAO.classes.GameDAOImpl;
import DAO.classes.LibraryDAOImpl;
import DAO.classes.UserDAOImpl;
import DAO.interfaces.CatalogDAO;
import DAO.interfaces.GameDAO;
import DAO.interfaces.UserDAO;
import DTO.CatalogDTO;
import DTO.GameDTO;
import DTO.UserDTO;
import entities.*;
import services.GameService;
import services.TransactionHandler;
import services.UserService;
import utils.converter.CatalogConverter;
import utils.converter.GameConverter;
import utils.converter.UserConverter;
import utils.roles.Roles;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        UserDAO userDAO = new UserDAOImpl();
        UserDTO userDTO = userService.registerUser("admin","admin@gmail.com","root","toor");
        User user = userDAO.read(2);
        user.setRole(Roles.ADMIN);
        userDAO.update(user.getId(), user);
    }

}