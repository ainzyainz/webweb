package servlets.authorization;

import DTO.UserDTO;
import services.classes.ChangePassHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.constant.ConstantsContainer.*;

@WebServlet(name = "ChangePassServlet", urlPatterns = {"/changePass"})
public class ChangePassServlet extends HttpServlet {

    private ChangePassHandler changePassHandler = new ChangePassHandler();
    private Logger LOGGER = Logger.getLogger(ChangePassServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO user = (UserDTO) req.getSession().getAttribute(CURRENT_MSG);
        if (user == null) {
            LOGGER.log(Level.INFO, USER_IS_NULL);
            req.setAttribute(WRONG_MSG, true);
            req.getRequestDispatcher("signUp.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("changePass.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String newPass = req.getParameter(NEW_MSG);
        UserDTO current = (UserDTO) req.getSession().getAttribute(CURRENT_MSG);

        if (newPass == null) {
            LOGGER.log(Level.INFO, PASSWORD_IS_NULL);
            resp.sendRedirect("/forgotPass.jsp");
            return;
        }
        changePassHandler.updatePassword(current.getId(), newPass);
        current.setPassword(newPass);

        req.getSession().setAttribute(CURRENT_MSG, current);
        req.getSession().setAttribute(PASSWORD_MSG, newPass);
        req.getSession().setAttribute(EMAIL_MSG, current.getEmail());
        LOGGER.log(Level.INFO, PASSWORD_CHANGE_SUCCESS + newPass);
        resp.sendRedirect("/signUp");
    }
}
