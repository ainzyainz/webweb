package servlets;

import DTO.BinDTO;
import DTO.CatalogDTO;
import DTO.GameDTO;
import DTO.UserDTO;
import services.GameService;
import services.TransactionHandler;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "StoreServlet", urlPatterns = {"/mainPage"})
public class StoreServlet extends HttpServlet {
    private GameService gameService = new GameService();
    private TransactionHandler transactionHandler = new TransactionHandler();
    private Logger LOGGER = Logger.getLogger(StoreServlet.class.getName());
    private final UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action==null){
            getList(req,resp);
            return;
        }
        switch (action){
            case "buy": buyGame(req,resp);
                break;
            case "toBin" : addToBin(req,resp);
            break;
            case "fromBin" : removeFromBin(req,resp);
            break;
            case "getBin" : getBin(req,resp);
            break;
            case"buyBin" : buyAllFromBin(req,resp);
            break;
            case "read" : readGame(req,resp);
            break;
            case "change" : changeProfile(req,resp);
        }
    }
    public void changeProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String address = req.getParameter("address");
        String userAge = req.getParameter("age");
        req.getSession().setAttribute("current",userService.updateUser(userId,name,surname,address,userAge));
        getList(req,resp);
    }
    public void readGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search");
        Set<CatalogDTO> catalogDTOSet = gameService.getAllCatalogs();
        req.getSession().setAttribute("catalogs",catalogDTOSet);
        req.getSession().setAttribute("games",gameService.searchGame(search));
        req.getRequestDispatcher("/mainPage.jsp").forward(req,resp);
    }

    public void buyAllFromBin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("current");
        if (transactionHandler.buyAllFromBin(userDTO)!=1){
            LOGGER.log(Level.INFO,"Couldn't buy all games from bin");
            resp.sendRedirect("mainPage");
            return;
        }
        LOGGER.log(Level.INFO,"Bought all games from bin");
        req.getSession().setAttribute("current",userDTO);
        resp.sendRedirect("mainPage");
    }

    public void removeFromBin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("current");
        if (userService.removeFromBin(id,userDTO)!=1){
            LOGGER.log(Level.INFO,"Couldn't remove game from bin");
            return;
        }
        req.getSession().setAttribute("current",userDTO);
        LOGGER.log(Level.INFO,"Removed game from user's bin");
        getBin(req,resp);
    }

    public void getBin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("current");
        req.setAttribute("games",userDTO.getBinDTO().getGameDTOSet());
        req.getRequestDispatcher("/bin.jsp").forward(req,resp);
    }

    public void addToBin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("current");
        if (userService.addToBin(id,userDTO)!=1){
            LOGGER.log(Level.INFO,"User couldn't add game to a bin");
            resp.sendRedirect("mainPage");
            return;
        }
        LOGGER.log(Level.INFO,"User "+userDTO.getUserDescriptionDTO().getName()+ " added game to their bin");
        req.getSession().setAttribute("current",userDTO);
        resp.sendRedirect("mainPage");
    }
    public void buyGame(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("current");
        if (transactionHandler.buyGame(id,userDTO)!=1){
            LOGGER.log(Level.INFO,"User couldn't buy a game");
            resp.sendRedirect("mainPage");
            return;
        }
        LOGGER.log(Level.INFO,"User "+ userDTO.getUserDescriptionDTO().getName() + " bought a game");
        req.getSession().setAttribute("current",userDTO);
        resp.sendRedirect("mainPage");
    }
    public void getList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int page = 1;
        int perPage = 5;
        if (req.getParameter("page")!=null){
            page = Integer.parseInt(req.getParameter("page"));
        }
        int pages = gameService.getNoOfPages(perPage);
        Set<CatalogDTO> catalogDTOSet = gameService.getAllCatalogs();
        Set<GameDTO> allGames = gameService.getGamesLimited((page-1)*perPage,perPage);
        req.getSession().setAttribute("catalogs",catalogDTOSet);
        req.getSession().setAttribute("games",allGames);
        req.getSession().setAttribute("noOfPages",pages);
        req.getSession().setAttribute("currentPage",page);
        req.getRequestDispatcher("/mainPage.jsp").forward(req,resp);
    }

}
