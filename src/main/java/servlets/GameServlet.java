package servlets;

import DTO.GameDTO;
import DTO.ReviewDTO;
import DTO.UserDTO;
import services.classes.GameService;
import services.classes.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.constant.ConstantsContainer.*;

@WebServlet(name = GAME_SERVLET, urlPatterns = {DASH + CURRENTGAME_URL})
public class GameServlet extends HttpServlet {
    private Logger LOGGER = Logger.getLogger(GameServlet.class.getName());
    private GameService gameService = new GameService();
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(ACTION_);
        if (action == null) {
            getGame(req, resp);
            return;
        }
        switch (action) {
            case ACTION_GETGAME:
                getGame(req, resp);
                break;
            case ACTION_WRITEREVIEW:
                writeReview(req, resp);
                break;
            default:
                LOGGER.log(Level.INFO, UNKNOWN_ACTION_MSG);
                resp.sendRedirect(UNKNOWNACTION_URL + JSP);
        }
    }

    public void getGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter(ID_MSG);
        GameDTO gameDTO = gameService.findById(id);
        Set<ReviewDTO> reviews = gameService.getGameReviews(gameDTO);
        if (id.isBlank() || gameDTO == null) {
            LOGGER.log(Level.INFO, GAME_IS_NULL);
            resp.sendRedirect(NOSUCHGAME_URL + JSP);
            return;
        }
        if (reviews == null) {
            LOGGER.log(Level.INFO, NULL_MSG_WRITEREVIEW);
            reviews = new HashSet<>();
        }
        req.setAttribute(REVIEWS_MSG, reviews);
        req.setAttribute(GAME_MSG, gameDTO);
        req.getRequestDispatcher(DASH + CURRENTGAME_URL + JSP).forward(req, resp);
    }

    public void writeReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter(TEXT_MSG);
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute(CURRENT_MSG);
        String id = req.getParameter(ID_MSG);
        GameDTO gameDTO = gameService.findById(id);
        if (!userService.writeReview(text, userDTO, gameDTO)) {
            req.setAttribute(WRONG_MSG, true);
            resp.sendRedirect(MAINPAGE_URL);
        }
        getGame(req, resp);
    }
}
