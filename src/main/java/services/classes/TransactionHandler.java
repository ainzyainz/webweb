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
import utils.hibernate.HibernateUtils;

import javax.persistence.EntityManager;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        BinDTO binDTO = userDTO.getBinDTO();
        if (binDTO.getGameDTOSet().isEmpty()){
            LOGGER.log(Level.INFO,userDTO.getEmail()+" bin is empty.");
            return false;
        }
        double userBalance = userDTO.getBalanceDTO().getBalance();
        double total = 0;
        for (GameDTO temp : binDTO.getGameDTOSet()) {
            total += temp.getGameStatisticsDTO().getPrice();
            userDTO.getLibraryDTO().getGameDTOSet().add(temp);
        }
        if (userDAO.checkBalance(userBalance, total) == 0) {
            LOGGER.log(Level.INFO, "User " + userDTO.getEmail() + " has not enough money to buy all games from bin");
            return false;
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
        return true;
    }
    @Override
    public boolean buyGame(String game_id, UserDTO userDTO) {
        int id;
        try{
            id = Integer.parseInt(game_id);
        }catch (NumberFormatException e){
            LOGGER.log(Level.INFO,"Id is not a nubmer");
            return false;
        }

        double userBalance = userDTO.getBalanceDTO().getBalance();
        Game game = gameDAO.read(id);
        if (game==null){
            LOGGER.log(Level.INFO,"No game found");
            return false;
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
                return false;
            }
            GameDTO gameDTO = gameConverter.applyDTO(game);
            userDTO.getLibraryDTO().getGameDTOSet().add(gameDTO);

            userDAO.update(user.getId(), user);
            gameDAO.update(game.getId(), game);

            LOGGER.log(Level.INFO, "User " + userDTO.getEmail() + " bought a game "+game.getName());
            return true;
        }
        LOGGER.log(Level.INFO, "User " + userDTO + " has not enough money");
        return false;
    }


}