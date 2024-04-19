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

@WebServlet(name = "VerifyCodeServlet", urlPatterns = {"/verifyCode"})
public class VerifyCodeServlet extends HttpServlet {

    private ChangePassHandler changePassHandler = new ChangePassHandler();
    private Logger LOGGER = Logger.getLogger(ChangePassServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter(EMAIL_MSG);
        UserDTO userDTO = changePassHandler.isValid(email);

        if (userDTO != null) {
            req.getSession().setAttribute(CURRENT_MSG, userDTO);
            req.getSession().setAttribute(EMAIL_MSG, email);
            req.getSession().setAttribute(CODE_MSG, changePassHandler.sendEmail(email));
            req.getRequestDispatcher("verifyCode.jsp").forward(req, resp);
        } else {
            LOGGER.log(Level.INFO, USER_IS_NULL);
            req.setAttribute(WRONG_MSG, true);
            req.getRequestDispatcher("forgotPass.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        if (req.getSession().getAttribute(CODE_MSG) == null || req.getParameter(INPUTCODE_MSG) == null) {
            LOGGER.log(Level.INFO, CODE_IS_NULL);
            req.setAttribute(WRONG_MSG, true);
            req.getRequestDispatcher("verifyCode.jsp").forward(req, resp);
        }
        int code;
        int inputCode;
        try {
            code = (int) req.getSession().getAttribute(CODE_MSG);
            inputCode = Integer.parseInt(req.getParameter(INPUTCODE_MSG));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.INFO, PARSING_CODE_MSG);
            req.setAttribute(WRONG_MSG, true);
            req.getRequestDispatcher("verifyCode.jsp").forward(req, resp);
            return;
        }

        if (changePassHandler.codeVerified(code, inputCode)) {
            resp.sendRedirect("changePass");
        } else {
            LOGGER.log(Level.INFO, MATCH_PASS_FAIL + code + "!=" + inputCode);
            req.setAttribute(WRONG_MSG, true);
            req.getRequestDispatcher("verifyCode.jsp").forward(req, resp);
        }
    }
}