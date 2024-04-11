package servlets.authorization;

import DTO.UserDTO;
import services.ChangePassHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ChangePassServlet", urlPatterns = {"/changePass"})
public class ChangePassServlet extends HttpServlet {

    private ChangePassHandler changePassHandler = new ChangePassHandler();
    private Logger LOGGER = Logger.getLogger(ChangePassServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO user = (UserDTO) req.getSession().getAttribute("current");
        if (user == null) {
            LOGGER.log(Level.INFO, "User is null");
            req.setAttribute("wrong", true);
            req.getRequestDispatcher("signUp.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("changePass.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String newPass = req.getParameter("new");
        UserDTO current = (UserDTO) req.getSession().getAttribute("current");

        if (newPass == null) {
            LOGGER.log(Level.INFO, "New password is null");
            resp.sendRedirect("/forgotPass.jsp");
            return;
        }
        changePassHandler.updatePassword(current.getId(), newPass);
        current.setPassword(newPass);

        req.getSession().setAttribute("current", current);
        req.getSession().setAttribute("password", newPass);
        req.getSession().setAttribute("email", current.getEmail());
        LOGGER.log(Level.INFO, "Changed password successfully");
        resp.sendRedirect("/signUp");
    }
}
