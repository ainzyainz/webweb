package servlets;

import DTO.UserDTO;
import services.TransactionHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "BalanceServlet", urlPatterns = {"/balance","/balance/addFive","/balance/addTen","/balance/addTwenty","/balance/addFifty","/balance/addGrand"})

public class BalanceServlet extends HttpServlet {

    private TransactionHandler transactionHandler = new TransactionHandler();
    private Logger LOGGER = Logger.getLogger(BalanceServlet.class.getName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("current");
        String action = req.getParameter("action");
        switch (action){
            case "addFive" : transactionHandler.toBalance(userDTO,5);
            break;
            case "addTen" : transactionHandler.toBalance(userDTO,10);
            break;
            case "addTwenty" : transactionHandler.toBalance(userDTO,20);
            break;
            case "addFifty" : transactionHandler.toBalance(userDTO,50);
            break;
            case "addGrand" : transactionHandler.toBalance(userDTO,100);
            default: LOGGER.log(Level.INFO,"A problem occurred during balance transaction");
            break;
        }
        req.getRequestDispatcher("/profile.jsp").forward(req,resp);
    }
}
