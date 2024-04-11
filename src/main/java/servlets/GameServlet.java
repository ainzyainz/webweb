package servlets;

import DTO.CatalogDTO;
import DTO.GameDTO;
import services.GameService;

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

@WebServlet(name = "GameServlet", urlPatterns = {"/currentGame"})
public class GameServlet extends HttpServlet {
    private Logger LOGGER = Logger.getLogger(GameServlet.class.getName());
    private GameService gameService = new GameService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        GameDTO gameDTO = gameService.getGameByName(name);
        if (name==null||gameDTO==null){
            LOGGER.log(Level.INFO,"Game name is null");
            resp.sendRedirect("mainPage");
            return;
        }
        req.setAttribute("game",gameDTO);
        req.getRequestDispatcher("/currentGame.jsp").forward(req,resp);
    }
}
