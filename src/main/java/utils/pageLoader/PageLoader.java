package utils.pageLoader;

import DTO.LibraryPageDTO;
import DTO.StorePageDTO;
import DTO.UserDTO;
import services.classes.GameService;
import services.classes.UserService;

import java.util.logging.Logger;

import static utils.constant.ConstantsContainer.PAGE_DEFAULT;
import static utils.constant.ConstantsContainer.PER_PAGE_DEFAULT;

public class PageLoader {

    private GameService gameService = new GameService();
    private Logger LOGGER = Logger.getLogger(PageLoader.class.getName());
    private UserService userService = new UserService();

    public LibraryPageDTO buildLibraryPage(String currentPage, UserDTO current) {
        int page = PAGE_DEFAULT;
        if(current==null) {
            return null;
        }
        LibraryPageDTO libraryPageDTO = LibraryPageDTO.builder()
                .page(page)
                .games(gameService.getLimited(current.getId(),page,PER_PAGE_DEFAULT))
                .noOfPages(userService.getNoOfPages(PER_PAGE_DEFAULT,current))
                .perPage(PER_PAGE_DEFAULT)
                .build();
        if (currentPage==null){
            return libraryPageDTO;
        }
        try{
            page = Integer.parseInt(currentPage);
        }catch (NumberFormatException e){
            LOGGER.info("Failed parsing page number.");
            return libraryPageDTO;
        }
        libraryPageDTO.setPage(page);
        return libraryPageDTO;
    }

    public StorePageDTO buildStorePage(String currentPage) {
        int page = PAGE_DEFAULT;
        StorePageDTO storePageDTO = StorePageDTO.builder()
                .page(page)
                .perPage(PER_PAGE_DEFAULT)
                .noOfPages(gameService.getNoOfPages(PER_PAGE_DEFAULT))
                .catalogDTOSet(gameService.getAllCatalogs())
                .allGames(gameService.getGamesLimited(page, PER_PAGE_DEFAULT))
                .best(gameService.getBest())
                .build();

        if (currentPage != null) {
            try {
                page = Integer.parseInt(currentPage);
            } catch (NumberFormatException e) {
                LOGGER.info("Failed parsing page number.");
                return storePageDTO;
            }
            storePageDTO.setPage(page);
        }
        return storePageDTO;
    }
}
