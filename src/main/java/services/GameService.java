package services;

import DAO.classes.CatalogDAOImpl;
import DAO.classes.GameDAOImpl;
import DAO.classes.GameRequirementsDAOImpl;
import DAO.interfaces.GameRequirementsDAO;
import DTO.CatalogDTO;
import DTO.GameDTO;
import entities.*;
import utils.converter.CatalogConverter;
import utils.converter.GameConverter;
import utils.converter.GameReqsConverter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class GameService {
    private final Logger LOGGER = Logger.getLogger(GameService.class.getName());
    private final GameDAOImpl gameDAO = new GameDAOImpl();
    private final GameRequirementsDAO gameRequirementsDAO = new GameRequirementsDAOImpl();

    private final GameConverter gameConverter = new GameConverter();
    private final GameReqsConverter gameReqsConverter = new GameReqsConverter();
    private final CatalogConverter catalogConverter = new CatalogConverter();
    private final CatalogDAOImpl catalogDAO = new CatalogDAOImpl();

    public Set<GameDTO> searchGame(String search){
        Set<GameDTO> gameDTOSet = new HashSet<>();
        gameDAO.findBySearch(search).stream()
                .map(gameConverter::applyDTO)
                .forEach(gameDTOSet::add);

        gameRequirementsDAO.findBySearch(search)
                .forEach(q -> gameDTOSet.add(gameConverter.applyDTO(q.getGame())));

        return gameDTOSet;
    }

    public GameDTO getGameByName(String name){
        Game game = gameDAO.getGameByName(name);
        if (game==null){
            LOGGER.log(Level.INFO,"No such game found");
            return null;
        }
            return gameConverter.applyDTO(game);
    }
    public Set<GameDTO> getAllGames(){
        List<Game> games = gameDAO.getAllGames();
        Set<GameDTO> gameDTOSet = new HashSet<>();
        games.stream().map(gameConverter::applyDTO).forEach(gameDTOSet::add);
        return gameDTOSet;
    }
    public GameDTO createGame(String game_price, String name, String OS, String Processor, String Memory, String Graphics, String DirectX, String Storage) {
        double price;
        try{
            price = Integer.parseInt(game_price);
        }catch (NumberFormatException e){
            LOGGER.log(Level.INFO,"Failed creating game. Price is not a number");
            return null;
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
                .graphics(Graphics)
                .directX(DirectX)
                .memory(Memory)
                .OS(OS)
                .processor(Processor)
                .storage(Storage)
                .game(game)
                .build();

        game.setStats(gameStatistics);
        game.setReqs(gameRequirements);
        gameDAO.create(game);
        return gameConverter.applyDTO(game);
    }

    public Set<CatalogDTO> getAllCatalogs(){
        List<Catalog> catalogs = catalogDAO.getAllCatalogs();
        Set<CatalogDTO> catalogDTOSet = new HashSet<>();
        catalogs.stream().map(catalogConverter::applyDTO).forEach(catalogDTOSet::add);
        return catalogDTOSet;
    }
    public Set<GameDTO> getGamesByCatalog(String id){
        int intId;
        try{
            intId = Integer.parseInt(id);
        }catch (NumberFormatException e){
            LOGGER.log(Level.INFO,"id is not a number");
            return null;
        }
        Catalog catalog = catalogDAO.read(intId);
        return catalog.getGames()
                .stream()
                .map(gameConverter::applyDTO)
                .collect(Collectors.toSet());
    }

    public Set<GameDTO> getGamesLimited(int x, int y){
        Set<Game> set = gameDAO.getLimited(x,y);
        Set<GameDTO> gameDTOSet = new HashSet<>();
        set.stream().map(gameConverter::applyDTO).forEach(gameDTOSet::add);
        return gameDTOSet;
    }
    public int getNoOfPages(int perPage){
        int total = getAllGames().size();
        int noOfPages = (int) Math.ceil(total * 1.0 / perPage);
        return noOfPages;
    }
    public int addToCatalog(String catalogId, String gameId) {
        long idCatalog = 0;
        long idGame = 0;
        try{
            idCatalog = Long.parseLong(catalogId);
            idGame = Long.parseLong(gameId);
        }catch(NumberFormatException e){
            LOGGER.log(Level.INFO,"Couldn't add to catalog. Id is not a number");
        }

        Game game = gameDAO.read(idGame);
        Catalog catalog = catalogDAO.read(idCatalog);

        game.getCatalogs().add(catalog);
        if (catalog.getGames().size()==game.getCatalogs().size()){
            LOGGER.log(Level.INFO,"The game is already in the catalog");
            return -1;
        }
        gameDAO.update(game.getId(),game);
        LOGGER.log(Level.INFO,"Added game "+game.getName()+" to a catalog "+catalog.getName());
        return 1;
    }
    public int deleteCatalog(String catalog_id){
        int id;
        try{
            id = Integer.parseInt(catalog_id);
        }catch (NumberFormatException e){
            LOGGER.log(Level.INFO,"Can't delete catalog. Id is not a nubmer");
            return -1;
        }
        Catalog catalog = catalogDAO.read(id);
        catalog.getGames().stream().forEach(q -> q.getCatalogs().remove(catalog));
        catalogDAO.update(catalog.getId(),catalog);

        if (catalogDAO.delete(id) != -1) {
            LOGGER.log(Level.INFO, "deleted the catalog " + id);
            return 1;
        }
        LOGGER.log(Level.INFO, "failed deleting the catalog");
        return -1;
    }

    public int deleteGame(String game_id) {
        int id;
        try{
            id = Integer.parseInt(game_id);
        }catch (NumberFormatException e){
            LOGGER.log(Level.INFO,"Can't delete game. Id is not a nubmer");
            return -1;
        }
        Game game = gameDAO.read(id);
        Set<Library> libraries = game.getLibraries();
        Set<Bin> bins = game.getBins();

        libraries.forEach(q -> q.getGames().remove(game));
        bins.forEach(q -> q.getGames().remove(game));
        game.getBins().clear();
        game.getLibraries().clear();
        gameDAO.update(game.getId(),game);

        if (gameDAO.delete(id) != -1) {
            LOGGER.log(Level.INFO, "deleted the game " + id);
            return 1;
        }
        LOGGER.log(Level.INFO, "failed deleting the game");
        return -1;
    }
    
    public int updateGame(String game_price, String game_id, String name, String OS, String Processor, String Memory, String Graphics, String DirectX, String Storage) {
        long id = 0;
        double price = 0;
        try{
            price = Double.parseDouble(game_price);
            id = Long.parseLong(game_id);
        }catch (NumberFormatException e){
            LOGGER.log(Level.INFO,"Failed updating the game. Id is not a number");
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
            LOGGER.log(Level.INFO, "Service game update failed. No such game");
            return -1;
        } else {
            LOGGER.log(Level.INFO, "Service game update successfully");
            return 1;
        }
    }

}
