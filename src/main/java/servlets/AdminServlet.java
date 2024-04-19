package servlets;

import DTO.CatalogDTO;
import DTO.GameDTO;
import DTO.UserDTO;
import services.classes.GameService;
import services.classes.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static utils.constant.ConstantsContainer.*;

@WebServlet(name = ADMIN_SERVLET, urlPatterns = {DASH + MAINPAGEADMIN_URL})
public class AdminServlet extends HttpServlet {
    private UserService userService = new UserService();
    private GameService gameService = new GameService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(ACTION_MSG);
        if (action == null) {
            resp.sendRedirect(MAINPAGEADMIN_URL + JSP);
            return;
        }
        switch (action) {
            case USERS_MSG:
                getUsers(req, resp);
                break;
            case DELETEUSER_MSG:
                deleteUser(req, resp);
                break;
            case GAMES_MSG:
                getGames(req, resp);
                break;
            case DELETEGAME_MSG:
                deleteGame(req, resp);
            case CATALOGS_MSG:
                getCatalogs(req, resp);
                break;
            case DELETECATALOG_MSG:
                deleteCatalog(req, resp);
                break;
            case CREATEGAME_MSG:
                createGame(req, resp);
                break;
            case CREATEUSER_MSG:
                createUser(req, resp);
                break;
            case READUSER_MSG:
                readUser(req, resp);
                break;
            case READGAME_MSG:
                readGame(req, resp);
                break;
            case UPDATEUSER_MSG:
                updateUser(req, resp);
                break;
            case DISPLAYEDIT_MSG:
                displayEdit(req, resp);
                break;
            case DISPLAYEDITGAME_MSG:
                displayEditGame(req, resp);
                break;
            case UPDATEGAME_MSG:
                updateGame(req, resp);
                break;
            case TOCATALOG_MSG:
                addToCatalog(req, resp);
        }
    }

    public void addToCatalog(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String catalogId = req.getParameter(CATALOGID_MSG);
        String gameId = req.getParameter(ID_MSG);
        gameService.addToCatalog(catalogId, gameId);
        getGames(req, resp);
    }

    public void updateGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter(ID_MSG);
        String name = req.getParameter(NAME_MSG);
        String OS = req.getParameter(OS_MSG);
        String processor = req.getParameter(PROCESSOR_MSG);
        String memory = req.getParameter(MEMORY_MSG);
        String graphics = req.getParameter(GRAPHICS_MSG);
        String directX = req.getParameter(DIRECTX_MSG);
        String storage = req.getParameter(STORAGE_MSG);
        String price = req.getParameter(PRICE_MSG);
        gameService.updateGame(price, userId, name, OS, processor, memory, graphics, directX, storage);
        getGames(req, resp);
    }

    public void displayEditGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter(NAME_MSG);
        GameDTO gameDTO = gameService.searchGame(name)
                .stream()
                .findFirst()
                .orElse(null);
        req.setAttribute(GAMES_MSG, gameDTO);
        req.getRequestDispatcher(DASH + ADMINGAME_URL + JSP).forward(req, resp);
    }

    public void displayEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter(EMAIL_MSG);
        UserDTO userDTO = userService.searchUser(email)
                .stream()
                .findFirst()
                .orElse(null);
        req.setAttribute(USERS_MSG, userDTO);
        req.getRequestDispatcher(DASH + ADMINUSER_URL + JSP).forward(req, resp);
    }

    public void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter(ID_MSG);
        String name = req.getParameter(NAME_MSG);
        String surname = req.getParameter(SURNAME_MSG);
        String address = req.getParameter(ADDRESS_MSG);
        String userAge = req.getParameter(AGE_MSG);
        userService.updateUser(userId, name, surname, address, userAge);
        getUsers(req, resp);
    }

    public void readGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter(SEARCH_MSG);
        req.getSession().setAttribute(GAMES_MSG, gameService.searchGame(search));
        req.getRequestDispatcher(DASH + ADMINGAME_URL + JSP).forward(req, resp);
    }

    public void readUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter(SEARCH_MSG);
        req.getSession().setAttribute(USERS_MSG, userService.searchUser(search));
        req.getRequestDispatcher(DASH + ADMINUSER_URL + JSP).forward(req, resp);
    }

    public void createUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter(PASSWORD_MSG);
        String email = req.getParameter(EMAIL_MSG);
        String name = req.getParameter(NAME_MSG);
        String surname = req.getParameter(SURNAME_MSG);
        userService.registerUser(password, email, name, surname);
        getUsers(req, resp);
    }

    public void createGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String price = req.getParameter(PRICE_MSG);
        String name = req.getParameter(NAME_MSG);
        String OS = req.getParameter(OS_MSG);
        String processor = req.getParameter(PROCESSOR_MSG);
        String memory = req.getParameter(MEMORY_MSG);
        String graphics = req.getParameter(GRAPHICS_MSG);
        String directX = req.getParameter(DIRECTX_MSG);
        String storage = req.getParameter(STORAGE_MSG);
        gameService.createGame(price, name, OS, processor, memory, graphics, directX, storage);
        getGames(req, resp);
    }

    public void deleteCatalog(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter(ID_MSG);
        gameService.deleteCatalog(id);
        getCatalogs(req, resp);
    }

    public void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter(ID_MSG);
        userService.deleteUser(id);
        getUsers(req, resp);
    }

    public void deleteGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter(ID_MSG);
        gameService.deleteGame(id);
        getGames(req, resp);
    }

    public void getUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<UserDTO> userDTOSet = userService.getAllUsers();
        req.getSession().setAttribute(USERS_MSG, userDTOSet);
        req.getRequestDispatcher(DASH + ADMINUSER_URL + JSP).forward(req, resp);
    }

    public void getGames(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<GameDTO> allGames = gameService.getAllGames();
        req.getSession().setAttribute(GAMES_MSG, allGames);
        req.getRequestDispatcher(DASH + ADMINGAME_URL + JSP).forward(req, resp);
    }

    public void getCatalogs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<CatalogDTO> allCatalogs = gameService.getAllCatalogs();
        req.getSession().setAttribute(CATALOGS_MSG, allCatalogs);
        req.getRequestDispatcher(DASH + ADMINCATALOG_URL + JSP).forward(req, resp);
    }
}
