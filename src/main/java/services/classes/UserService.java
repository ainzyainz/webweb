package services.classes;

import DAO.classes.*;
import DAO.interfaces.GameDAO;
import DAO.interfaces.ReviewDAO;
import DTO.*;
import entities.*;
import services.interfaces.UserServiceInterface;
import utils.converter.GameConverter;
import utils.converter.ReviewConverter;
import utils.converter.UserConverter;
import utils.functionalinterface.MyInterfaceToDAO;
import utils.functionalinterface.UtilsInterface;
import utils.hibernate.HibernateUtils;
import utils.roles.Roles;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.constant.ConstantsContainer.*;

public class UserService implements UserServiceInterface {
    private EntityManager entityManager = HibernateUtils.getEntityManager();
    private final UserDAOImpl userDAO = new UserDAOImpl(entityManager);
    private final UserDescriptionDAOImpl userDescriptionDAO = new UserDescriptionDAOImpl(entityManager);
    private final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private final GameConverter gameConverter = new GameConverter();
    private final UserConverter userConverter = new UserConverter();
    private final ReviewConverter reviewConverter = new ReviewConverter();
    private final ReviewDAO reviewDAO = new ReviewDAOImpl(entityManager);
    private final GameDAO gameDAO = new GameDAOImpl(entityManager);

    public int getNoOfPages(int perPage, UserDTO userDTO){
        User user = userDAO.read(userDTO.getId());
        int total = user.getLibrary().getGames().size();
        return (int) Math.ceil(total * 1.0 / perPage);
    }
    @Override
    public boolean writeReview(String text, UserDTO userDTO,
                               GameDTO gameDTO) {
        if (text.isBlank() || userDTO == null || gameDTO == null) {
            LOGGER.log(Level.INFO, WRITEREVIEW_INPUT_MSG);
            return false;
        }
        MyInterfaceToDAO<ReviewDTO> betweenBeginAndCommit = () -> {

            User user = userDAO.read(userDTO.getId());
            Game game = gameDAO.read(gameDTO.getId());

            if (user == null || game == null) {
                LOGGER.log(Level.INFO, WRITEREVIEW_NULL_MSG);
                return null;
            }
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
            LOGGER.log(Level.INFO, WRITEREVIEW_SUCCESS + userDTO.getEmail());
            return reviewConverter.applyDTO(review);
        };
        ReviewDTO result = UtilsInterface.superMethodInterface(betweenBeginAndCommit, entityManager);
        return result != null;
    }

    @Override
    public UserDTO loginUser(String email, String password) {
        System.out.println("loginuser" + entityManager.isOpen());
        if (email.isBlank() || password.isBlank()) {
            LOGGER.log(Level.INFO, INPUT_MSG_LOGINUSER);
            return null;
        }
        MyInterfaceToDAO<User> betweenBeginAndCommited = () -> {
            List<User> users = userDAO.getUserByEmailAndPassword(email, password);
            return users == null ? null :
                    users.stream()
                            .findFirst().orElse(null);
        };
        User user = UtilsInterface.superMethodInterface(betweenBeginAndCommited, entityManager);
        return user != null ? userConverter.applyDTO(user) : null;
    }

    @Override
    public Set<UserDTO> getAllUsers() {
        MyInterfaceToDAO<Set<UserDTO>> betweenBeginAndCommited = () -> {
            List<User> users = userDAO.getAllUsers();
            if (users == null || users.isEmpty()) {
                return null;
            }
            Set<UserDTO> userDTOSet = new HashSet<>();
            users.stream().map(userConverter::applyDTO).forEach(userDTOSet::add);
            return userDTOSet;
        };
        return UtilsInterface.superMethodInterface(betweenBeginAndCommited, entityManager);
    }
    @Override
    public boolean removeFromBin(String game_id, UserDTO userDTO) {
        if (game_id.isBlank() || userDTO == null) {
            LOGGER.log(Level.INFO, INPUT_MSG_REMOVEFROMBIN);
            return false;
        }

        MyInterfaceToDAO<Boolean> betweenBeginAndCommited = () -> {
            int id;
            try {
                id = Integer.parseInt(game_id);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.INFO, PARSE_MSG_REMOVEFROMBIN);
                return false;
            }
            Game game = gameDAO.read(id);
            User user = userDAO.read(userDTO.getId());

            if (game == null || user == null) {
                LOGGER.log(Level.INFO, NULL_MSG_REMOVEFROMBIN);
                return false;
            }

            user.getBin().getGames().remove(game);
            game.getBins().remove(user.getBin());

            GameDTO gameDTO = gameConverter.applyDTO(game);

            userDTO.getBinDTO().getGameDTOSet().remove(gameDTO);

            userDAO.update(user.getId(), user);
            gameDAO.update(game.getId(), game);
            return true;
        };
        return UtilsInterface.superMethodInterface(betweenBeginAndCommited, entityManager);
    }

    @Override
    public boolean addToBin(String game_id, UserDTO userDTO) {
        if (game_id.isBlank() || userDTO == null) {
            LOGGER.log(Level.INFO, INPUT_MSG_ADDTOBIN);
            return false;
        }
        MyInterfaceToDAO<Boolean> betweenBeginAndCommited = () -> {
            int id;
            try {
                id = Integer.parseInt(game_id);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.INFO, PARSE_MSG_ADDTOBIN + game_id);
                return false;
            }
            Game game = gameDAO.read(id);
            User user = userDAO.read(userDTO.getId());

            if (user == null || game == null) {
                LOGGER.log(Level.INFO, NULL_MSG_ADDTOBIN);
                return false;
            }
            GameDTO gameDTO = gameConverter.applyDTO(game);
            user.getBin().getGames().add(game);
            game.getBins().add(user.getBin());

            if (user.getBin().getGames().size() == userDTO.getBinDTO().getGameDTOSet().size()) {
                LOGGER.log(Level.INFO, ADDTOBIN_ALREADY_EXIST + user.getEmail());
                return false;
            }

            userDTO.getBinDTO().getGameDTOSet().add(gameDTO);
            userDAO.update(user.getId(), user);
            gameDAO.update(game.getId(), game);
            return true;
        };
        return UtilsInterface.superMethodInterface(betweenBeginAndCommited, entityManager);
    }
    @Override
    public Set<GameDTO> getLibraryGames(UserDTO userDTO){
         if (userDTO==null){
            LOGGER.log(Level.INFO,"Incorrect input in getLibraryGames. User is null");
            return null;
        }
        MyInterfaceToDAO<Set<Game>> betweenBeginAndCommited = () -> {
            User user = userDAO.read(userDTO.getId());
            return user.getLibrary().getGames();
        };
        Set<Game> games = UtilsInterface.superMethodInterface(betweenBeginAndCommited,entityManager);
        Set<GameDTO> gameDTOSet = new HashSet<>();
        games.stream().map(gameConverter::applyDTO).forEach(gameDTOSet::add);
        return gameDTOSet;
    }

    @Override
    public UserDTO registerUser(String password, String email, String name, String surname) {
        if (password.isBlank() || email.isBlank() || name.isBlank() || surname.isBlank()) {
            LOGGER.log(Level.INFO, INPUT_MSG_REGISTERUSER);
            return null;
        }
        MyInterfaceToDAO<User> betweenBeginAndCommited = () -> {
            if (userDAO.getUserByEmail(email) != null) {
                LOGGER.log(Level.INFO, NULL_MSG_REGISTERUSER + email);
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
            userDAO.create(user);
            try {
                InternetAddress emailAddr = new InternetAddress(email);
                emailAddr.validate();
            } catch (AddressException e) {
                LOGGER.log(Level.INFO, VALIDATE_EMAIL_FAIL + email);
                return null;
            }
            LOGGER.log(Level.INFO, SUCCESS_REGISTERUSER + email);
            return user;
        };
        User result = UtilsInterface.superMethodInterface(betweenBeginAndCommited, entityManager);
        return result != null ? userConverter.applyDTO(result) : null;
    }

    @Override
    public Set<UserDTO> searchUser(String search) {
        if (search.isBlank()) {
            LOGGER.log(Level.INFO, INPUT_MSG_SEARCHUSER);
            getAllUsers();
        }
        MyInterfaceToDAO<Set<UserDTO>> betweenBeginAndCommited = () -> {
            Set<UserDTO> userDTOSet = new HashSet<>();
            userDAO.findBySearch(search).forEach(q -> userDTOSet.add(userConverter.applyDTO(q)));
            userDescriptionDAO.findBySearch(search).forEach(q -> userDTOSet.add(userConverter.applyDTO(q.getUser())));
            if (userDTOSet.isEmpty()) {
                LOGGER.log(Level.INFO, NULL_MSG_SEARCHUSER);
                return null;
            }
            return userDTOSet;
        };
        return UtilsInterface.superMethodInterface(betweenBeginAndCommited, entityManager);
    }

    @Override
    public boolean deleteUser(String user_id) {
        if (user_id.isBlank()) {
            LOGGER.log(Level.INFO, INPUT_MSG_DELETEUSER);
            return false;
        }
        MyInterfaceToDAO<User> betweenBeginAndCommited = () -> {
            int id;
            try {
                id = Integer.parseInt(user_id);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.INFO, PARSE_MSG_DELETEUSER);
                return null;
            }
            User user = userDAO.read(id);
            user.getLibrary().getGames().forEach(q -> q.getLibraries().remove(user.getLibrary()));
            user.getBin().getGames().forEach(q -> q.getBins().remove(user.getBin()));
            userDAO.update(user.getId(), user);

            if (!userDAO.delete(id)) {
                LOGGER.log(Level.INFO, NULL_MSG_DELETEUSER + id);
                return null;
            } else {
                LOGGER.log(Level.INFO, SUCCESS_DELETEUSER + id);
                return user;
            }
        };
        User result = UtilsInterface.superMethodInterface(betweenBeginAndCommited, entityManager);
        return result != null;
    }

    @Override
    public UserDTO updateUser(String user_id, String name,
                              String surname, String address,
                              String user_age) {
        if (user_id.isBlank() || name.isBlank() || surname.isBlank() || address.isBlank() || user_age.isBlank()) {
            LOGGER.log(Level.INFO, INPUT_MSG_UPDATEUSER);
            return null;
        }
        MyInterfaceToDAO<User> betweenBeginAndCommited = () -> {
            long id;
            int age;
            try {
                id = Long.parseLong(user_id);
                age = Integer.parseInt(user_age);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.INFO, "Couldn't update user. Id or age are not numbers");
                return null;
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
            return user;
        };

        User result = UtilsInterface.superMethodInterface(betweenBeginAndCommited, entityManager);
        return result != null ? userConverter.applyDTO(result) : null;
    }
}
