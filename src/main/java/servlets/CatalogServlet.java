package servlets;

import DTO.GameDTO;
import services.classes.GameService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static utils.constant.ConstantsContainer.*;

@WebServlet(name = CATALOG_SERVLET, urlPatterns = {DASH + CURRENTCATALOG_URL})
public class CatalogServlet extends HttpServlet {
    private GameService gameService = new GameService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(ID_MSG);
        Set<GameDTO> gameDTOSet = gameService.getGamesByCatalog(action);
        System.out.println(gameDTOSet);
        req.setAttribute(GAMES_MSG, gameDTOSet);
        req.getRequestDispatcher(DASH + CURRENTCATALOG_URL + JSP).forward(req, resp);
    }
}
