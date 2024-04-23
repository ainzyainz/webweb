package servlets;

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
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.constant.ConstantsContainer.*;

@WebServlet(name = USER_SERVLET, urlPatterns = {DASH + USER_URL})
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserService();
    private final Logger LOGGER = Logger.getLogger(UserServlet.class.getName());
    private final GameService gameService = new GameService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(ACTION_);
        if (action == null) {
            getProfile(req, resp);
            return;
        }
        switch (action) {
            case ACTION_CHANGE:
                changeProfile(req, resp);
                break;
            case ACTION_GETPROFILE:
                getProfile(req, resp);
                break;
            case ACTION_GETLIBRARY:
                getLibrary(req, resp);
                break;
            default:
                LOGGER.log(Level.INFO, "Invalid action input in userServlet");
                resp.sendRedirect("unknownAction.jsp");
        }
    }

    public void getProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }

    public void changeProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String userId = req.getParameter(ID_MSG);
        String name = req.getParameter(NAME_MSG);
        String surname = req.getParameter(SURNAME_MSG);
        String address = req.getParameter(ADDRESS_MSG);
        String userAge = req.getParameter(AGE_MSG);
        req.getSession().setAttribute(CURRENT_MSG, userService.updateUser(userId, name, surname, address, userAge));
        getProfile(req, resp);
    }

    public void getLibrary(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int page = PAGE_DEFAULT;
        int perPage = PER_PAGE_DEFAULT;
        UserDTO current = (UserDTO) req.getSession().getAttribute("current");
        String currentPage = req.getParameter(PAGE_MSG);
        int intPage;
        if (currentPage != null) {
            try {
                intPage = Integer.parseInt(currentPage);
                perPage = intPage;
            } catch (NumberFormatException e) {
                LOGGER.log(Level.INFO, PARSE_MSG_GETLIST);
            }
        }
        int pages = gameService.getNoOfPages(perPage);
        Set<GameDTO> libraryGames = userService.getLibraryGames(current);
        req.setAttribute(NOOFPAGES_MSG, pages);
        req.setAttribute(GAMES_MSG, libraryGames);
        req.setAttribute(CURRENTPAGE_MSG, page);
        req.getRequestDispatcher(LIBRARY_URL + JSP).forward(req, resp);
    }
}

