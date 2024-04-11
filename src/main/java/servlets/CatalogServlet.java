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

@WebServlet(name = "CatalogServlet", urlPatterns = {"/currentCatalog"})
public class CatalogServlet extends HttpServlet {
    private GameService gameService = new GameService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String action = req.getParameter("id");
    Set<GameDTO> gameDTOSet = gameService.getGamesByCatalog(action);
        System.out.println(gameDTOSet);
    req.setAttribute("games",gameDTOSet);
    req.getRequestDispatcher("/currentCatalog.jsp").forward(req,resp);
    }
}
