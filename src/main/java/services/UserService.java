package services;

import DAO.classes.*;
import DAO.interfaces.BinDAO;
import DAO.interfaces.GameDAO;
import DAO.interfaces.ReviewDAO;
import DTO.*;
import entities.*;
import utils.converter.GameConverter;
import utils.converter.UserConverter;
import utils.roles.Roles;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.time.Instant;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UserService {
    private final UserDAOImpl userDAO = new UserDAOImpl();
    private final UserDescriptionDAOImpl userDescriptionDAO = new UserDescriptionDAOImpl();
    private final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private final GameConverter gameConverter = new GameConverter();
    private final UserConverter userConverter = new UserConverter();

    private final ReviewDAO reviewDAO = new ReviewDAOImpl();
    private final GameDAO gameDAO = new GameDAOImpl();
    private final BinDAO binDAO = new BinDAOImpl();

    public void writeReview(String text, UserDTO userDTO, GameDTO gameDTO) {
        User user = userDAO.read(userDTO.getId());
        Game game = gameDAO.read(gameDTO.getId());
        Review review = Review.builder()
                .reviewText(text)
                .user(user)
                .game(game)
                .build();

        user.getReviews().add(review);
        game.getReviews().add(review);

        reviewDAO.create(review);
        userDAO.update(user.getId(), user);
        gameDAO.update(game.getId(), game);
    }

    public UserDTO loginUser(String email, String password) {
        List<User> users = userDAO.getUserByEmailAndPassword(email, password);
        if (users != null) {
            return userConverter.applyDTO(users.get(0));
        }
        LOGGER.log(Level.INFO, "No such user found");
        return null;
    }

    public Set<UserDTO> getAllUsers(){
        List<User> users = userDAO.getAllUsers();
        Set<UserDTO> userDTOSet = new HashSet<>();
        users.stream().map(userConverter::applyDTO).forEach(userDTOSet::add);
        return userDTOSet;
    }
    public void clearBin(UserDTO userDTO) {
        User user = userDAO.read(userDTO.getId());
        for (Game game : user.getBin().getGames()) {
            game.getBins().remove(user.getBin());
            gameDAO.update(game.getId(), game);
        }
        user.getBin().getGames().clear();
        userDAO.update(user.getId(), user);
    }

    public int removeFromBin(String game_id, UserDTO userDTO) {
        int id;
        try{
            id = Integer.parseInt(game_id);
        }catch (NumberFormatException e){
            LOGGER.log(Level.INFO,"id is not a number");
            return -1;
        }
        Game game = gameDAO.read(id);

        if (userDTO == null || game == null) {
            LOGGER.log(Level.INFO, "Trouble occurred during removing from bin");
            return -1;
        }
        User user = userDAO.read(userDTO.getId());

        user.getBin().getGames().remove(game);
        game.getBins().remove(user.getBin());

        GameDTO gameDTO = gameConverter.applyDTO(game);

        userDTO.getBinDTO().getGameDTOSet().remove(gameDTO);

        userDAO.update(user.getId(), user);
        gameDAO.update(game.getId(), game);

        LOGGER.log(Level.INFO, "Removed " + gameDTO.getName() + " from " + userDTO.getUserDescriptionDTO().getName() + " bin");
        return 1;
    }

    public int addToBin(String game_id, UserDTO userDTO) {
        int id;
        try {
            id = Integer.parseInt(game_id);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.INFO, "Game id is not a number");
            return -1;
        }
        Game game = gameDAO.read(id);
        if (userDTO == null || game == null) {
            LOGGER.log(Level.INFO, "Trouble occurred during adding to bin");
            return -1;
        }
        GameDTO gameDTO = gameConverter.applyDTO(game);
        User user = userDAO.read(userDTO.getId());
        user.getBin().getGames().add(game);
        game.getBins().add(user.getBin());
        if (user.getBin().getGames().size() == userDTO.getBinDTO().getGameDTOSet().size()) {
            LOGGER.log(Level.INFO, "Bin already has this game");
            return -1;
        }
        userDTO.getBinDTO().getGameDTOSet().add(gameDTO);
        userDAO.update(user.getId(), user);
        gameDAO.update(game.getId(), game);
        LOGGER.log(Level.INFO, "Added " + gameDTO.getName() + " to " + userDTO.getUserDescriptionDTO().getName() + " bin");
        return 1;
    }

    public UserDTO registerUser(String password, String email, String name, String surname) {
        if (password.isEmpty() || email.isEmpty() || name.isEmpty() || surname.isEmpty()) {
            return null;
        }
        if (userDAO.getUserByEmail(email) != null) {
            return null;
        }
        User user = User.builder()
                .email(email)
                .password(password)
                .reviews(new HashSet<>())
                .role(Roles.USER)
                .build();
        Balance balance = Balance.builder()
                .balance(0)
                .user(user)
                .build();

        Bin bin = Bin
                .builder()
                .user(user)
                .games(new HashSet<>())
                .build();
        UserDescription userDescription = UserDescription.builder()
                .name(name)
                .surname(surname)
                .address("")
                .user(user)
                .age(-1)
                .build();
        Library library = Library.builder()
                .user(user)
                .games(new HashSet<>())
                .build();

        user.setBin(bin);
        user.setLibrary(library);
        user.setDescription(userDescription);
        user.setBalance(balance);

        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException e) {
            LOGGER.log(Level.INFO, "Email wasn't written correctly");
            return null;
        }

        if (userDAO.create(user) == null) {
            LOGGER.log(Level.INFO, "Failed to register a user");
            return null;
        } else {
            LOGGER.log(Level.INFO, "Registered a user " + user.getEmail());
            return userConverter.applyDTO(user);
        }
    }


    public Set<UserDTO> searchUser(String search) {
        Set<UserDTO> userDTOSet = new HashSet<>();
        userDAO.findBySearch(search).forEach(q -> userDTOSet.add(userConverter.applyDTO(q)));
        userDescriptionDAO.findBySearch(search).forEach(q -> userDTOSet.add(userConverter.applyDTO(q.getUser())));
        return userDTOSet;
    }


    public void deleteUser(String user_id) {
        int id;
        try{
            id = Integer.parseInt(user_id);
        }catch (NumberFormatException e){
            LOGGER.log(Level.INFO,"Failed deleting user. Id is not a number");
            return;
        }
        User user = userDAO.read(id);
        user.getLibrary().getGames().forEach(q -> q.getLibraries().remove(user.getLibrary()));
        user.getBin().getGames().forEach(q -> q.getBins().remove(user.getBin()));
        userDAO.update(user.getId(),user);

        if (userDAO.delete(id) == -1) {
            LOGGER.log(Level.INFO, "Deleting user failed");
        } else {
            LOGGER.log(Level.INFO, "User " + id + " deleted");
        }
    }

    public UserDTO updateUser(String user_id, String name, String surname, String address, String user_age) {
        long id = 0;
        int age = -1;
        try{
            id = Long.parseLong(user_id);
            age = Integer.parseInt(user_age);
        }catch (NumberFormatException e){
            LOGGER.log(Level.INFO,"Couldn't update user. Id or age are not numbers");
        }


        User user = userDAO.read(id);
        UserDescription userDescription = user.getDescription();
        userDescription.setName(name);
        userDescription.setSurname(surname);
        userDescription.setAddress(address);
        userDescription.setAge(age);

        if (userDAO.update(id, user) == null) {
            LOGGER.log(Level.INFO, "Updating user failed. No such user");
            return null;
        }

            LOGGER.log(Level.INFO, "User " + id + " updated");
            return userConverter.applyDTO(user);
    }
}
