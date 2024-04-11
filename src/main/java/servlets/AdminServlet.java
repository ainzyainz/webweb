package servlets;

import DTO.CatalogDTO;
import DTO.GameDTO;
import DTO.UserDTO;
import services.GameService;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet(name = "AdminServlet", urlPatterns = {"/mainPageAdmin"})
public class AdminServlet extends HttpServlet {
    private UserService userService = new UserService();
    private GameService gameService = new GameService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action==null){
            resp.sendRedirect("mainPageAdmin.jsp");
            return;
        }
        switch (action){
            case "users":getUsers(req,resp);
            break;
            case "deleteUser":deleteUser(req,resp);
            break;
            case "games":getGames(req,resp);
            break;
            case "deleteGame":deleteGame(req,resp);
            case "catalogs":getCatalogs(req,resp);
            break;
            case "deleteCatalog":deleteCatalog(req,resp);
            break;
            case "createGame":createGame(req,resp);
            break;
            case "createUser":createUser(req,resp);
            break;
            case "readUser":readUser(req,resp);
            break;
            case "readGame":readGame(req,resp);
            break;
            case "updateUser":updateUser(req,resp);
            break;
            case "displayEdit":displayEdit(req,resp);
            break;
            case "displayEditGame": displayEditGame(req,resp);
            break;
            case "updateGame":updateGame(req,resp);
            break;
            case "toCatalog":addToCatalog(req,resp);
        }
    }
    public void addToCatalog(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String catalogId = req.getParameter("catalog_id");
        String gameId = req.getParameter("id");
        gameService.addToCatalog(catalogId,gameId);
        getGames(req,resp);
    }
    public void updateGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        String name = req.getParameter("name");
        String OS = req.getParameter("OS");
        String processor = req.getParameter("processor");
        String memory = req.getParameter("memory");
        String graphics = req.getParameter("graphics");
        String directX = req.getParameter("directX");
        String storage = req.getParameter("storage");
        String price = req.getParameter("price");
        gameService.updateGame(price,userId,name,OS,processor,memory,graphics,directX,storage);
        getGames(req,resp);
    }

        public void displayEditGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        GameDTO gameDTO = gameService.searchGame(name).stream().findFirst().orElse(null);
        req.setAttribute("game",gameDTO);
        req.getRequestDispatcher("/adminGame.jsp").forward(req,resp);
    }
    public void displayEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        UserDTO userDTO = userService.searchUser(email).stream().findFirst().orElse(null);
        req.setAttribute("user",userDTO);
        req.getRequestDispatcher("/adminUser.jsp").forward(req,resp);
    }

        public void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String address = req.getParameter("address");
        String userAge = req.getParameter("age");
        userService.updateUser(userId,name,surname,address,userAge);
        getUsers(req,resp);
    }
        public void readGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search");
        req.getSession().setAttribute("games",gameService.searchGame(search));
        req.getRequestDispatcher("/adminGame.jsp").forward(req,resp);
    }
    public void readUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String search = req.getParameter("search");
    req.getSession().setAttribute("users",userService.searchUser(search));
    req.getRequestDispatcher("/adminUser.jsp").forward(req,resp);
    }

        public void createUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        userService.registerUser(password,email,name,surname);
        getUsers(req,resp);
    }

        public void createGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String price = req.getParameter("price");
        String name = req.getParameter("name");
        String OS = req.getParameter("OS");
        String processor = req.getParameter("processor");
        String memory = req.getParameter("memory");
        String graphics = req.getParameter("graphics");
        String directX = req.getParameter("directX");
        String storage = req.getParameter("storage");
        gameService.createGame(price,name,OS,processor,memory,graphics,directX,storage);
        getGames(req,resp);
    }
    public void deleteCatalog(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        gameService.deleteCatalog(id);
        getCatalogs(req,resp);
    }
    public void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        userService.deleteUser(id);
        getUsers(req,resp);
    }
    public void deleteGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        gameService.deleteGame(id);
        getGames(req,resp);
    }
    public void getUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<UserDTO> userDTOSet = userService.getAllUsers();
        req.getSession().setAttribute("users",userDTOSet);
        req.getRequestDispatcher("/adminUser.jsp").forward(req,resp);
    }

    public void getGames(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<GameDTO> allGames = gameService.getAllGames();
        req.getSession().setAttribute("games",allGames);
        req.getRequestDispatcher("/adminGame.jsp").forward(req,resp);
    }
    public void getCatalogs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<CatalogDTO> allCatalogs = gameService.getAllCatalogs();
        req.getSession().setAttribute("catalogs",allCatalogs);
        req.getRequestDispatcher("/adminCatalog.jsp").forward(req,resp);
    }

}
