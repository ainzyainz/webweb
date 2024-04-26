package services.classes;

import DAO.classes.CatalogDAOImpl;
import DAO.classes.GameDAOImpl;
import DAO.classes.GameRequirementsDAOImpl;
import DAO.classes.UserDAOImpl;
import DAO.interfaces.GameRequirementsDAO;
import DAO.interfaces.UserDAO;
import DTO.CatalogDTO;
import DTO.GameDTO;
import DTO.ReviewDTO;
import DTO.UserDTO;
import entities.*;
import services.interfaces.GameServiceInterface;
import utils.converter.CatalogConverter;
import utils.converter.GameConverter;
import utils.converter.GameReqsConverter;
import utils.converter.ReviewConverter;
import utils.functionalinterface.MyInterfaceToDAO;
import utils.functionalinterface.UtilsInterface;
import utils.hibernate.HibernateUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static utils.constant.ConstantsContainer.*;

public class GameService implements GameServiceInterface {
    private final Logger LOGGER = Logger.getLogger(GameService.class.getName());
    private final EntityManager entityManager = HibernateUtils.getEntityManager();
    private final GameDAOImpl gameDAO = new GameDAOImpl(entityManager);
    private final GameRequirementsDAO gameRequirementsDAO = new GameRequirementsDAOImpl(entityManager);
    private final GameConverter gameConverter = new GameConverter();
    private final CatalogConverter catalogConverter = new CatalogConverter();
    private final CatalogDAOImpl catalogDAO = new CatalogDAOImpl(entityManager);
    private final ReviewConverter reviewConverter = new ReviewConverter();
    private final UserDAO userDAO = new UserDAOImpl(entityManager);

    @Override
    public Set<GameDTO> searchGame(String search) {
        MyInterfaceToDAO<List<Game>> betwenBeginAndCommited = () -> {
            List<Game> gameSet = new ArrayList<>();

            gameDAO.findBySearch(search)
                    .forEach(gameSet::add);

            gameRequirementsDAO.findBySearch(search)
                    .forEach(q -> gameSet.add(q.getGame()));

            return gameSet;
        };
        List<Game> games = UtilsInterface.superMethodInterface(betwenBeginAndCommited, entityManager);
        return games.stream().map(gameConverter::applyDTO).collect(Collectors.toSet());
    }

    @Override
    public GameDTO getGameByName(String name) {
        if (name.isBlank()) {
            LOGGER.log(Level.INFO, INPUT_MSG_GETGAMEBYGAME);
            return null;
        }
        MyInterfaceToDAO<Game> betwenBeginAndCommited = () -> {
            Game game = gameDAO.getGameByName(name);
            if (game == null) {
                LOGGER.log(Level.INFO, GETBYGAME_NOT_FOUND + name);
                return null;
            }
            return game;
        };
        Game result = UtilsInterface.superMethodInterface(betwenBeginAndCommited, entityManager);
        return result == null ? null : gameConverter.applyDTO(result);
    }

    @Override
    public Set<GameDTO> getAllGames() {
        MyInterfaceToDAO<List<Game>> betweenBeginAndCommitted = () -> {
            List<Game> games = gameDAO.getAllGames();
            return games;
        };
        List<Game> resultList = UtilsInterface.superMethodInterface(betweenBeginAndCommitted, entityManager);
        if (resultList == null) {
            LOGGER.log(Level.INFO, GETALLGAMES_RESULT_LIST_NULL);
            return new HashSet<>();
        }
        Set<GameDTO> gameDTOSet = new HashSet<>();
        resultList.stream().map(gameConverter::applyDTO).forEach(gameDTOSet::add);
        return gameDTOSet;
    }

    @Override
    public GameDTO createGame(String game_price, String name,
                              String OS, String processor,
                              String memory, String graphics,
                              String directX, String storage) {
        if (game_price.isBlank() || name.isBlank() ||
                OS.isBlank() || processor.isBlank() ||
                memory.isBlank() || graphics.isBlank() ||
                directX.isBlank() || storage.isBlank()) {
            LOGGER.log(Level.INFO, INPUT_MSG_CREATEGAME);
            return null;
        }
        double price;
        try {
            price = Integer.parseInt(game_price);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.INFO, PARSE_MSG_CREATEGAME + game_price);
            return null;
        }
        if (price < 0) {
            LOGGER.log(Level.INFO, INCORRECT_PRICE + game_price);
        }
        Game game = Game.builder()
                .name(name)
                .bins(new HashSet<>())
                .libraries(new HashSet<>())
                .catalogs(new HashSet<>())
                .reviews(new HashSet<>())
                .build();

        GameStatistics gameStatistics = GameStatistics.builder()
                .purchaseCounter(0)
                .price(price)
                .game(game)
                .build();

        GameRequirements gameRequirements = GameRequirements.builder()
                .graphics(graphics)
                .directX(directX)
                .memory(memory)
                .OS(OS)
                .processor(processor)
                .storage(storage)
                .game(game)
                .build();

        game.setStats(gameStatistics);
        game.setReqs(gameRequirements);
        MyInterfaceToDAO<Game> betweenBeginAndCommitted = () -> gameDAO.create(game);
        UtilsInterface.superMethodInterface(betweenBeginAndCommitted, entityManager);
        return gameConverter.applyDTO(game);
    }

    @Override
    public Set<ReviewDTO> getGameReviews(GameDTO gameDTO) {
        if (gameDTO == null) {
            LOGGER.log(Level.INFO, "Incorrect input in getGameReviews. Game is null");
            return null;
        }
        MyInterfaceToDAO<Set<Review>> betweenBeginAndCommited = () -> {
            Game game = gameDAO.read(gameDTO.getId());
            return game.getReviews();
        };
        Set<Review> reviews = UtilsInterface.superMethodInterface(betweenBeginAndCommited, entityManager);
        Set<ReviewDTO> reviewDTOSet = new HashSet<>();
        reviews.stream().map(reviewConverter::applyDTO).forEach(reviewDTOSet::add);
        return reviewDTOSet;
    }

    @Override
    public GameDTO findById(String game_id) {
        if (game_id.isBlank()) {
            LOGGER.log(Level.INFO, "Failed to find a game. Incorrect input in findById.");
            return null;
        }
        MyInterfaceToDAO<Game> betweenBeginAndCommited = () -> {
            long id;
            try {
                id = Long.parseLong(game_id);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.INFO, "Failed to parse value in findById. ID must be a number");
                return null;
            }
            return gameDAO.read(id);
        };
        Game game = UtilsInterface.superMethodInterface(betweenBeginAndCommited, entityManager);
        return game != null ? gameConverter.applyDTO(game) : null;
    }

    @Override
    public Set<CatalogDTO> getAllCatalogs() {
        MyInterfaceToDAO<List<Catalog>> betweenBeginAndCommitted = catalogDAO::getAllCatalogs;
        List<Catalog> catalogs = UtilsInterface.superMethodInterface(betweenBeginAndCommitted, entityManager);
        Set<CatalogDTO> catalogDTOSet = new HashSet<>();
        catalogs.stream().map(catalogConverter::applyDTO).forEach(catalogDTOSet::add);
        return catalogDTOSet;
    }

    @Override
    public Set<GameDTO> getGamesByCatalog(String id) {
        if (id.isBlank()) {
            LOGGER.log(Level.INFO, INPUT_MSG_GETGAMESBYCATALOG);
            return null;
        }
        MyInterfaceToDAO<Catalog> betweenBeginAndCommit = () -> {
            int intId;
            try {
                intId = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.INFO, PARSE_MSG_GETGAMESBYCATALOG + id);
                return null;
            }
            return catalogDAO.read(intId);
        };
        Catalog catalog = UtilsInterface.superMethodInterface(betweenBeginAndCommit, entityManager);

        return catalog.getGames()
                .stream()
                .map(gameConverter::applyDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<GameDTO> getLimited(long id, int x, int y) {
        User user = userDAO.read(id);
        if (user == null) {
            LOGGER.log(Level.INFO, "User is null. Failed to getLimited");
            return null;
        }
        List<Game> games = new ArrayList<>(user.getLibrary().getGames());
        if (x < 0 || y < 0) {
            LOGGER.log(Level.INFO, INPUT_MSG_GETGAMESLIMITED + y);
            return new HashSet<>();
        }
        Set<GameDTO> result = new HashSet<>();
        for (int i = (x - 1) * y; i < y * x; i++) {
            if (i < games.size()) {
                result.add(gameConverter.applyDTO(games.get(i)));
            } else {
                break;
            }
        }
        return result;
    }

    @Override
    public Set<GameDTO> getGamesLimited(int x, int y) {
        int start = (x - 1) * y;
        int amount = y;
        if (start < 0 || amount < 0) {
            LOGGER.log(Level.INFO, INPUT_MSG_GETGAMESLIMITED + y);
            return new HashSet<>();
        }
        MyInterfaceToDAO<Set<Game>> betweenBeginAndCommit = () -> gameDAO.getLimited(start, amount);
        Set<Game> resultSet = UtilsInterface.superMethodInterface(betweenBeginAndCommit, entityManager);
        Set<GameDTO> gameDTOSet = new HashSet<>();
        resultSet.stream().map(gameConverter::applyDTO).forEach(gameDTOSet::add);
        return gameDTOSet;
    }

    @Override
    public int getNoOfPages(int perPage) {
        if (perPage < 1) {
            LOGGER.log(Level.INFO, INPUT_MSG_GETNOOFPAGES + perPage);
            return 0;
        }
        int total = getAllGames().size();
        int noOfPages = (int) Math.ceil(total * 1.0 / perPage);
        return noOfPages;
    }

    @Override
    public boolean addToCatalog(String catalogId, String gameId) {
        if (catalogId.isBlank() || gameId.isBlank()) {
            LOGGER.log(Level.INFO, INPUT_MSG_ADDTOCATALOG);
            return false;
        }
        long idCatalog;
        long idGame;
        try {
            idCatalog = Long.parseLong(catalogId);
            idGame = Long.parseLong(gameId);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.INFO, PARSE_MSG_ADDTOCATALOG);
            return false;
        }

        MyInterfaceToDAO<Game> betweenBeginAndCommitted = () -> {
            Game game = gameDAO.read(idGame);
            Catalog catalog = catalogDAO.read(idCatalog);

            game.getCatalogs().add(catalog);
            if (catalog.getGames().size() == game.getCatalogs().size()) {
                LOGGER.log(Level.INFO, ALREADY_EXISTS);
                return null;
            }
            LOGGER.log(Level.INFO, game.getName() + ADDTOCATALOG_SUCCESS + catalog.getName());
            return gameDAO.update(game.getId(), game);
        };
        Game result = UtilsInterface.superMethodInterface(betweenBeginAndCommitted, entityManager);
        return result != null;
    }

    @Override
    public boolean deleteCatalog(String catalog_id) {
        if (catalog_id.isBlank()) {
            LOGGER.log(Level.INFO, INPUT_MSG_DELETECATALOG);
            return false;
        }
        int id;
        try {
            id = Integer.parseInt(catalog_id);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.INFO, PARSE_MSG_DELETECATALOG);
            return false;
        }
        MyInterfaceToDAO<Catalog> betweenBeginAndCommit = () -> {
            Catalog catalog = catalogDAO.read(id);
            catalog.getGames().forEach(q -> q.getCatalogs().remove(catalog));
            catalogDAO.update(catalog.getId(), catalog);

            if (!catalogDAO.delete(id)) {
                LOGGER.log(Level.INFO, DELETE_FAIL + catalog_id);
                return null;
            }
            LOGGER.log(Level.INFO, DELETE_SUCCESS + id);
            return catalog;
        };
        Catalog result = UtilsInterface.superMethodInterface(betweenBeginAndCommit, entityManager);
        return result != null;
    }

    @Override
    public boolean deleteGame(String game_id) {
        int id;
        try {
            id = Integer.parseInt(game_id);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.INFO, PARSE_MSG_DELETEGAME + game_id);
            return false;
        }
        Game game = gameDAO.read(id);
        Set<Library> libraries = game.getLibraries();
        Set<Bin> bins = game.getBins();

        libraries.forEach(q -> q.getGames().remove(game));
        bins.forEach(q -> q.getGames().remove(game));
        game.getBins().clear();
        game.getLibraries().clear();
        gameDAO.update(game.getId(), game);

        return gameDAO.delete(id);
    }

    @Override
    public boolean updateGame(String game_price, String game_id,
                              String name, String OS,
                              String Processor, String Memory,
                              String Graphics, String DirectX,
                              String Storage) {
        long id = 0;
        double price = 0;
        try {
            price = Double.parseDouble(game_price);
            id = Long.parseLong(game_id);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.INFO, PARSE_MSG_UPDATEGAME + game_id + "," + game_price);
        }
        Game game = gameDAO.read(id);

        GameStatistics gameStatistics = game.getStats();
        gameStatistics.setPrice(price);
        GameRequirements gameRequirements = game.getReqs();
        gameRequirements.setGraphics(Graphics);
        gameRequirements.setDirectX(DirectX);
        gameRequirements.setMemory(Memory);
        gameRequirements.setOS(OS);
        gameRequirements.setProcessor(Processor);
        gameRequirements.setStorage(Storage);

        game.setStats(gameStatistics);
        game.setReqs(gameRequirements);
        game.setName(name);

        if (gameDAO.update(id, game) == null) {
            LOGGER.log(Level.INFO, UPDATE_FAILED + name);
            return false;
        }
        LOGGER.log(Level.INFO, UPDATE_SUCCESS + name);
        return true;
    }
    @Override
    public Set<GameDTO> getBest(){
        Set<Game> bestSellers = gameDAO.getBest();
        if (bestSellers==null){
            bestSellers = new HashSet<>();
        }
    return bestSellers.stream()
            .map(gameConverter::applyDTO)
            .collect(Collectors.toSet());
    }
}