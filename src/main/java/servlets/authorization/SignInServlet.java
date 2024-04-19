package servlets.authorization;

import DTO.UserDTO;
import services.classes.UserService;
import utils.roles.Roles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static utils.constant.ConstantsContainer.*;

@WebServlet(name = "SignInServlet", urlPatterns = {"/signIn"})
public class SignInServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/signIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter(EMAIL_MSG);
        String password = req.getParameter(PASSWORD_MSG);
        UserDTO userDTO = userService.loginUser(email, password);

        if (userDTO==null){
            req.setAttribute(WRONG_MSG,true);
            doGet(req,resp);
            return;
        }

        req.getSession().setAttribute(CURRENT_MSG, userDTO);
        req.getSession().setAttribute(PASSWORD_MSG, password);
        req.getSession().setAttribute(EMAIL_MSG, email);
        req.getSession().setAttribute(ROLE_MSG, userDTO.getRole());

        if (userDTO.getRole()==Roles.ADMIN){
         resp.sendRedirect("mainPageAdmin");
         return;
        }
        resp.sendRedirect("mainPage");
    }
}
