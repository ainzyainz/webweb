package services.interfaces;

import DTO.CatalogDTO;
import DTO.GameDTO;
import DTO.ReviewDTO;

import java.util.List;
import java.util.Set;

public interface GameServiceInterface {
    Set<GameDTO> getLimited(long id, int x, int y);
    Set<GameDTO> searchGame(String search);

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

    Set<ReviewDTO> getGameReviews(GameDTO gameDTO);

    GameDTO getGameByName(String name);

    GameDTO findById(String game_id);
}
