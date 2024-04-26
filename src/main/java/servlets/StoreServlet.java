package servlets;

import DTO.CatalogDTO;
import DTO.GameDTO;
import DTO.UserDTO;
import services.classes.GameService;
import services.classes.TransactionHandler;
import services.classes.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.constant.ConstantsContainer.*;

@WebServlet(name = STORE_SERVLET, urlPatterns = {DASH + MAINPAGE_URL})
public class StoreServlet extends HttpServlet {
    private GameService gameService = new GameService();
    private TransactionHandler transactionHandler = new TransactionHandler();
    private Logger LOGGER = Logger.getLogger(StoreServlet.class.getName());
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(ACTION_);
        if (action == null) {
            getList(req, resp);
            return;
        }
        switch (action) {
            case ACTION_BUY:
                buyGame(req, resp);
                break;
            case ACTION_TOBIN:
                addToBin(req, resp);
                break;
            case ACTION_FROMBIN:
                removeFromBin(req, resp);
                break;
            case ACTION_GETBIN:
                getBin(req, resp);
                break;
            case ACTION_BUYBIN:
                buyAllFromBin(req, resp);
                break;
            case ACTION_READ:
                readGame(req, resp);
                break;
            default:
                LOGGER.log(Level.INFO, UNKNOWN_ACTION_MSG);
                resp.sendRedirect(UNKNOWNACTION_URL + JSP);
        }
    }

    public void readGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter(SEARCH_MSG);
        Set<CatalogDTO> catalogDTOSet = gameService.getAllCatalogs();
        req.setAttribute(CATALOGS_MSG, catalogDTOSet);
        req.setAttribute(GAMES_MSG, gameService.searchGame(search));
        req.getRequestDispatcher(DASH + MAINPAGE_URL + JSP).forward(req, resp);
    }

    public void buyAllFromBin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("current");
        if (!transactionHandler.buyAllFromBin(userDTO)) {
            LOGGER.log(Level.INFO, BUYALL_FAILED);
            req.setAttribute(FAILED_MSG, true);
            req.setAttribute(GAMES_MSG, userDTO.getBinDTO().getGameDTOSet());
            req.getRequestDispatcher(DASH + BIN_URL + JSP).forward(req, resp);
            return;
        }
        LOGGER.log(Level.INFO, BUYALL_SUCCESS);
        req.getSession().setAttribute(CURRENT_MSG, userDTO);
        resp.sendRedirect(MAINPAGE_URL);
    }

    public void removeFromBin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter(ID_MSG);
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("current");
        if (!userService.removeFromBin(id, userDTO)) {
            LOGGER.log(Level.INFO, REMOVEFROMBIN_FAILED + userDTO.getEmail());
            return;
        }
        req.getSession().setAttribute(CURRENT_MSG, userDTO);
        LOGGER.log(Level.INFO, REMOVEFROMBIN_SUCCESS + userDTO.getEmail());
        getBin(req, resp);
    }

    public void getBin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute(CURRENT_MSG);
        req.setAttribute(GAMES_MSG, userDTO.getBinDTO().getGameDTOSet());
        req.getRequestDispatcher(DASH + BIN_URL + JSP).forward(req, resp);
    }

    public void addToBin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter(ID_MSG);
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute(CURRENT_MSG);
        if (!userService.addToBin(id, userDTO)) {
            LOGGER.log(Level.INFO, ADDTOBIN_FAILED + userDTO.getEmail());
            resp.sendRedirect(MAINPAGE_URL);
            return;
        }
        LOGGER.log(Level.INFO, ADDTOBIN_SUCCESS + userDTO.getEmail());
        req.getSession().setAttribute(CURRENT_MSG, userDTO);
        resp.sendRedirect(MAINPAGE_URL);
    }

    public void buyGame(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter(ID_MSG);
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute(CURRENT_MSG);
        if (!transactionHandler.buyGame(id, userDTO)) {
            LOGGER.log(Level.INFO, BUYGAME_FAILED + id);
            resp.sendRedirect(MAINPAGE_URL);
            return;
        }
        LOGGER.log(Level.INFO, BUYGAME_SUCCESS + id);
        req.getSession().setAttribute(CURRENT_MSG, userDTO);
        resp.sendRedirect(MAINPAGE_URL);
    }

    public void getList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int page = PAGE_DEFAULT;
        int perPage = PER_PAGE_DEFAULT;
        String currentPage = req.getParameter(PAGE_MSG);
        int intPage;
        if (currentPage != null) {
            try {
                intPage = Integer.parseInt(currentPage);
                page = intPage;
            } catch (NumberFormatException e) {
                LOGGER.log(Level.INFO, "Failed parsing during getList. Page must be a number");
            }
        }

        int pages = gameService.getNoOfPages(perPage);
        Set<CatalogDTO> catalogDTOSet = gameService.getAllCatalogs();
        Set<GameDTO> allGames = gameService.getGamesLimited(page, perPage);
        Set<GameDTO> best = gameService.getBest();

        req.setAttribute(CATALOGS_MSG, catalogDTOSet);
        req.setAttribute(GAMES_MSG, allGames);
        req.setAttribute(NOOFPAGES_MSG, pages);
        req.setAttribute("best", best);
        req.setAttribute(CURRENTPAGE_MSG, page);
        req.getRequestDispatcher(DASH + MAINPAGE_URL + JSP).forward(req, resp);
    }
}
