package servlets;

import DTO.UserDTO;
import services.classes.TransactionHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.constant.ConstantsContainer.*;

@WebServlet(name = BALANCE_SERVLET, urlPatterns = {
        DASH+BALANCE_URL,DASH+BALANCE_URL+DASH+ADDFIVE_URL,
        DASH+BALANCE_URL+DASH+ADDTEN_URL,DASH+BALANCE_URL+DASH+ADDTWENTY_URL,
        DASH+BALANCE_URL+DASH+ADDFIFTY_URL,DASH+BALANCE_URL+DASH+ADDGRAND_URL})

public class BalanceServlet extends HttpServlet {

    private TransactionHandler transactionHandler = new TransactionHandler();
    private Logger LOGGER = Logger.getLogger(BalanceServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute(CURRENT_MSG);
        String action = req.getParameter(ACTION_);
        switch (action){
            case ACTION_ADDFIVE: transactionHandler.toBalance(userDTO,5);
            break;
            case ACTION_ADDTEN: transactionHandler.toBalance(userDTO,10);
            break;
            case ACTION_ADDTWENTY: transactionHandler.toBalance(userDTO,20);
            break;
            case ACTION_ADDFIFTY: transactionHandler.toBalance(userDTO,50);
            break;
            case ACTION_ADDGRAND: transactionHandler.toBalance(userDTO,100);
            default:
                LOGGER.log(Level.INFO,NO_ACTION_MSG);
                resp.sendRedirect(BALANCEERROR_URL+JSP);
            break;
        }
        req.getRequestDispatcher(DASH+PROFILE_URL+JSP).forward(req,resp);
    }
}
