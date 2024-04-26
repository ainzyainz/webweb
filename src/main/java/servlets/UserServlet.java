package servlets;

import DTO.GameDTO;
import DTO.LibraryPageDTO;
import DTO.UserDTO;
import services.classes.GameService;
import services.classes.UserService;
import utils.hibernate.HibernateUtils;
import utils.pageLoader.PageLoader;

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
    private final PageLoader pageLoader = new PageLoader();

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
            case ACTION_EXIT:
                exitProfile(req, resp);
                break;
            default:
                LOGGER.log(Level.INFO, INVALID_ACTION_USER+action);
                resp.sendRedirect(UNKNOWNACTION_URL+JSP);
        }
    }

    public void getProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher(DASH + PROFILE_URL + JSP).forward(req, resp);
    }
    public void exitProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HibernateUtils.closeEntityManager();
        req.getSession().setAttribute(CURRENT_MSG, null);
        req.getSession().setAttribute(PASSWORD_MSG, null);
        req.getSession().setAttribute(EMAIL_MSG, null);
        req.getSession().setAttribute(ROLE_MSG, null);
        resp.sendRedirect(SIGNIN_URL);
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
        UserDTO current = (UserDTO) req.getSession().getAttribute("current");
        String currentPage = req.getParameter(PAGE_MSG);

        LibraryPageDTO libraryPage = pageLoader.buildLibraryPage(currentPage,current);

        req.setAttribute(GAMES_MSG, libraryPage.getGames());
        req.setAttribute(PAGE_MSG, libraryPage.getPage());
        req.setAttribute(NOOFPAGES_MSG, libraryPage.getNoOfPages());
        req.setAttribute(PER_PAGE_MSG, libraryPage.getNoOfPages());
        req.getRequestDispatcher(DASH + LIBRARY_URL + JSP).forward(req, resp);
    }

}

