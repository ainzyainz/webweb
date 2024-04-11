package services;

import DAO.classes.*;
import DAO.interfaces.BalanceDAO;
import DAO.interfaces.GameDAO;
import DAO.interfaces.LibraryDAO;
import DTO.BalanceDTO;
import DTO.BinDTO;
import DTO.GameDTO;
import DTO.UserDTO;
import entities.Balance;
import entities.Bin;
import entities.Game;
import entities.User;
import utils.converter.GameConverter;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.constant.ConstantsContainer.BOUGHT_GAME_MSG;

public class TransactionHandler {
    private final UserDAOImpl userDAO = new UserDAOImpl();
    private final UserDescriptionDAOImpl userDescriptionDAO = new UserDescriptionDAOImpl();
    private final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private GameDAO gameDAO = new GameDAOImpl();
    private final UserService userService = new UserService();
    private final BalanceDAO balanceDAO = new BalanceDAOImpl();
    private final GameConverter gameConverter = new GameConverter();
    private final LibraryDAO libraryDAO = new LibraryDAOImpl();

    public void fromBalance(User user, double num) {
        double current = user.getBalance().getBalance();
        user.getBalance().setBalance(current - num);
    }


    public void toBalance(UserDTO userDTO, double num) {
        User user = userDAO.read(userDTO.getId());
        double current = userDTO.getBalanceDTO().getBalance();
        user.getBalance().setBalance(current + num);
        userDTO.getBalanceDTO().setBalance(current + num);
        userDAO.update(user.getId(), user);
    }

    public int buyAllFromBin(UserDTO userDTO) {
        BinDTO binDTO = userDTO.getBinDTO();
        if (binDTO.getGameDTOSet().isEmpty()){
            LOGGER.log(Level.INFO,userDTO.getEmail()+" bin is empty.");
            return -1;
        }
        double userBalance = userDTO.getBalanceDTO().getBalance();
        double total = 0;
        for (GameDTO temp : binDTO.getGameDTOSet()) {
            total += temp.getGameStatisticsDTO().getPrice();
            userDTO.getLibraryDTO().getGameDTOSet().add(temp);
        }
        if (userDAO.checkBalance(userBalance, total) == 0) {
            LOGGER.log(Level.INFO, "User " + userDTO.getEmail() + " has not enough money to buy all games from bin");
            return -1;
        }
        User user = userDAO.read(userDTO.getId());
        fromBalance(user, total);
        Bin bin = user.getBin();
        LOGGER.log(Level.INFO, "User " + userDTO.getEmail() + "purchased all games from bin");

        for (Game game : bin.getGames()) {
            game.getStats().setPurchaseCounter(game.getStats().getPurchaseCounter() + 1);
            game.getLibraries().add(user.getLibrary());
            game.getBins().remove(bin);
            gameDAO.update(game.getId(),game);
            LOGGER.log(Level.INFO, "User " + userDTO.getEmail() + " bought a game "+game.getName());
        }

        user.getBin().getGames().clear();
        userDTO.getBinDTO().getGameDTOSet().clear();
        userDAO.update(user.getId(),user);
        return 1;
    }

    public int buyGame(String game_id, UserDTO userDTO) {
        int id;
        try{
            id = Integer.parseInt(game_id);
        }catch (NumberFormatException e){
            LOGGER.log(Level.INFO,"Id is not a nubmer");
            return -1;
        }

        double userBalance = userDTO.getBalanceDTO().getBalance();
        Game game = gameDAO.read(id);
        if (game==null){
            LOGGER.log(Level.INFO,"No game found");
            return -1;
        }
        double price = game.getStats().getPrice();
        if (userDAO.checkBalance(userBalance, price) == 1) {
            User user = userDAO.read(userDTO.getId());
            fromBalance(user, price);
            user.getLibrary().getGames().add(game);
            game.getStats().setPurchaseCounter(game.getStats().getPurchaseCounter() + 1);
            game.getLibraries().add(user.getLibrary());

            if(user.getLibrary().getGames().size()==userDTO.getLibraryDTO().getGameDTOSet().size()){
                LOGGER.log(Level.INFO,"User already owns the game");
                return -1;
            }
            GameDTO gameDTO = gameConverter.applyDTO(game);
            userDTO.getLibraryDTO().getGameDTOSet().add(gameDTO);

            userDAO.update(user.getId(), user);
            gameDAO.update(game.getId(), game);

            LOGGER.log(Level.INFO, "User " + userDTO.getEmail() + " bought a game "+game.getName());
            return 1;
        }
        LOGGER.log(Level.INFO, "User " + userDTO + " has not enough money");
        return -1;
    }


}