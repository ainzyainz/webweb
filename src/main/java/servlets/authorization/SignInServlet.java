package servlets.authorization;

import DTO.UserDTO;
import services.UserService;
import utils.roles.Roles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SignInServlet", urlPatterns = {"/signIn"})
public class SignInServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/signIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserDTO userDTO = userService.loginUser(email, password);

        if (userDTO==null){
            req.setAttribute("wrong",true);
            doGet(req,resp);
            return;
        }

        req.getSession().setAttribute("current", userDTO);
        req.getSession().setAttribute("password", password);
        req.getSession().setAttribute("email", email);
        req.getSession().setAttribute("role", userDTO.getRole());

        if (userDTO.getRole()==Roles.ADMIN){
         resp.sendRedirect("mainPageAdmin");
         return;
        }
        resp.sendRedirect("mainPage");
    }
}
