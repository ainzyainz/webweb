package servlets;

import DTO.GameDTO;
import services.classes.GameService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.constant.ConstantsContainer.*;

@WebServlet(name = GAME_SERVLET, urlPatterns = {DASH +CURRENTGAME_URL})
public class GameServlet extends HttpServlet {
    private Logger LOGGER = Logger.getLogger(GameServlet.class.getName());
    private GameService gameService = new GameService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter(NAME_MSG);
        GameDTO gameDTO = gameService.getGameByName(name);
        if (name.isBlank()||gameDTO==null){
            LOGGER.log(Level.INFO,GAME_IS_NULL);
            resp.sendRedirect(NOSUCHGAME_URL+JSP);
            return;
        }
        req.setAttribute(GAME_MSG,gameDTO);
        req.getRequestDispatcher(DASH +CURRENTGAME_URL+JSP).forward(req,resp);
    }
}
