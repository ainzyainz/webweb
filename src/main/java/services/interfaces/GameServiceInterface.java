package services.interfaces;

import DTO.CatalogDTO;
import DTO.GameDTO;

import java.util.Set;

public interface GameServiceInterface {
    Set<GameDTO> searchGame(String search);
    GameDTO getGameByName(String name);
    Set<GameDTO> getAllGames();
    GameDTO createGame(String game_price, String name,
                       String OS, String processor,
                       String memory, String graphics,
                       String directX, String storage);
    Set<CatalogDTO> getAllCatalogs();
    Set<GameDTO> getGamesByCatalog(String id);
    Set<GameDTO> getGamesLimited(int x, int y);
    int getNoOfPages(int perPage);
    boolean addToCatalog(String catalogId, String gameId);
    boolean deleteCatalog(String catalog_id);
    boolean deleteGame(String game_id);
    boolean updateGame(String game_price, String game_id,
                       String name, String OS,
                       String processor, String memory,
                       String graphics, String directX,
                       String storage);
}