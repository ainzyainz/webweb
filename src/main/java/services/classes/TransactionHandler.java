package services.classes;

import DAO.classes.*;
import DAO.interfaces.GameDAO;
import DTO.BinDTO;
import DTO.GameDTO;
import DTO.UserDTO;
import entities.Bin;
import entities.Game;
import entities.User;
import services.interfaces.TransactionInterface;
import utils.converter.GameConverter;
import utils.functionalinterface.MyInterfaceToDAO;
import utils.functionalinterface.UtilsInterface;
import utils.hibernate.HibernateUtils;

import javax.persistence.EntityManager;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.constant.ConstantsContainer.*;

public class TransactionHandler implements TransactionInterface {

    private final EntityManager entityManager = HibernateUtils.getEntityManager();
    private final UserDAOImpl userDAO = new UserDAOImpl(entityManager);
    private final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private GameDAO gameDAO = new GameDAOImpl(entityManager);
    private final GameConverter gameConverter = new GameConverter();

    @Override
    public void fromBalance(User user, double num) {
        double current = user.getBalance().getBalance();
        user.getBalance().setBalance(current - num);
    }

    @Override
    public void toBalance(UserDTO userDTO, double num) {
        User user = userDAO.read(userDTO.getId());
        double current = userDTO.getBalanceDTO().getBalance();
        user.getBalance().setBalance(current + num);
        userDTO.getBalanceDTO().setBalance(current + num);
        userDAO.update(user.getId(), user);
    }

    @Override
    public boolean buyAllFromBin(UserDTO userDTO) {
        if (userDTO == null) {
            LOGGER.log(Level.INFO, NULL_MSG_BUYALLFROMBIN);
            return false;
        }
        BinDTO binDTO = userDTO.getBinDTO();
        if (binDTO.getGameDTOSet().isEmpty()) {
            LOGGER.log(Level.INFO, EMPTY_BIN_MSG + userDTO.getEmail());
            return false;
        }

        MyInterfaceToDAO<UserDTO> betweenBeginAndCommited = () -> {
            double userBalance = userDTO.getBalanceDTO().getBalance();
            double total = 0;
            for (GameDTO temp : binDTO.getGameDTOSet()) {
                total += temp.getGameStatisticsDTO().getPrice();
                userDTO.getLibraryDTO().getGameDTOSet().add(temp);
            }
            if (userDAO.checkBalance(userBalance, total) == 0) {
                LOGGER.log(Level.INFO, NOT_ENOUGH_BUYALLFROMBIN + userDTO.getEmail());
                return null;
            }
            User user = userDAO.read(userDTO.getId());
            fromBalance(user, total);
            Bin bin = user.getBin();
            LOGGER.log(Level.INFO, BUYALLFROMBIN_SUCCESS + userDTO.getEmail());

            for (Game game : bin.getGames()) {
                game.getStats().setPurchaseCounter(game.getStats().getPurchaseCounter() + 1);
                game.getLibraries().add(user.getLibrary());
                game.getBins().remove(bin);
                user.getLibrary().getGames().add(game);
                gameDAO.update(game.getId(), game);
            }

            user.getBin().getGames().clear();
            userDTO.getBinDTO().getGameDTOSet().clear();
            userDAO.update(user.getId(), user);
            return userDTO;
        };
        return UtilsInterface.superMethodInterface(betweenBeginAndCommited, entityManager) != null;
    }

    @Override
    public boolean buyGame(String game_id, UserDTO userDTO) {
        int id;
        try {
            id = Integer.parseInt(game_id);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.INFO, PARSE_MSG_BUYGAME);
            return false;
        }

        double userBalance = userDTO.getBalanceDTO().getBalance();
        Game game = gameDAO.read(id);
        if (game == null) {
            LOGGER.log(Level.INFO, NULL_MSG_BUYGAME + game_id);
            return false;
        }
        double price = game.getStats().getPrice();
        if (userDAO.checkBalance(userBalance, price) == 1) {
            User user = userDAO.read(userDTO.getId());
            fromBalance(user, price);
            user.getLibrary().getGames().add(game);
            game.getStats().setPurchaseCounter(game.getStats().getPurchaseCounter() + 1);
            game.getLibraries().add(user.getLibrary());

            if (user.getLibrary().getGames().size() == userDTO.getLibraryDTO().getGameDTOSet().size()) {
                LOGGER.log(Level.INFO, FAILED_BUYGAME + game.getName());
                return false;
            }
            GameDTO gameDTO = gameConverter.applyDTO(game);
            userDTO.getLibraryDTO().getGameDTOSet().add(gameDTO);

            userDAO.update(user.getId(), user);
            gameDAO.update(game.getId(), game);

            LOGGER.log(Level.INFO, SUCCESS_BUYGAME + game.getName());
            return true;
        }
        LOGGER.log(Level.INFO, FAIL_BALANCE_GAME + game.getName());
        return false;
    }
}