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

@WebServlet(name = "VerifyCodeServlet", urlPatterns = {"/verifyCode"})
public class VerifyCodeServlet extends HttpServlet {

    private ChangePassHandler changePassHandler = new ChangePassHandler();
    private Logger LOGGER = Logger.getLogger(ChangePassServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        UserDTO userDTO = changePassHandler.isValid(email);

        if (userDTO != null) {
            req.getSession().setAttribute("current", userDTO);
            req.getSession().setAttribute("email", email);
            req.getSession().setAttribute("code", changePassHandler.sendEmail(email));
            req.getRequestDispatcher("verifyCode.jsp").forward(req, resp);
        } else {
            LOGGER.log(Level.INFO,"UserDTO is null");
            req.setAttribute("wrong", true);
            req.getRequestDispatcher("forgotPass.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        if (req.getSession().getAttribute("code") == null || req.getParameter("inputCode") == null) {
            LOGGER.log(Level.INFO, "Code or input are null");
            req.setAttribute("wrong", true);
            req.getRequestDispatcher("verifyCode.jsp").forward(req, resp);
        }
        int code;
        int inputCode;

        try {
            code = (int) req.getSession().getAttribute("code");
            inputCode = Integer.parseInt(req.getParameter("inputCode"));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.INFO, "Code or input are not a number");
            req.setAttribute("wrong", true);
            req.getRequestDispatcher("verifyCode.jsp").forward(req, resp);
            return;
        }

        if (changePassHandler.codeVerified(code, inputCode)) {
            resp.sendRedirect("changePass");
        } else {
            LOGGER.log(Level.INFO, "Failed to match the codes during password change");
            req.setAttribute("wrong", true);
            req.getRequestDispatcher("verifyCode.jsp").forward(req, resp);
        }
    }
}
