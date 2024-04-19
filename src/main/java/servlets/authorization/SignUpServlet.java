package servlets.authorization;

import DTO.UserDTO;
import services.classes.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static utils.constant.ConstantsContainer.*;

@WebServlet(name = "SignUpServlet", urlPatterns = {"/signUp"})
public class SignUpServlet extends HttpServlet {

    private final UserService userService = new UserService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/signUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter(EMAIL_MSG);
        String password = req.getParameter(PASSWORD_MSG);
        String name = req.getParameter(NAME_MSG);
        String surname = req.getParameter(SURNAME_MSG);

       UserDTO userDTO = userService.registerUser(password,email,name,surname);

      if (userDTO==null){
              req.setAttribute("wrong",true);
              doGet(req,resp);
              return;
      }
            req.getSession().setAttribute(CURRENT_MSG, userDTO);
            req.getSession().setAttribute(PASSWORD_MSG, password);
            req.getSession().setAttribute(EMAIL_MSG, email);
            req.getSession().setAttribute(ROLE_MSG, userDTO.getRole());

            resp.sendRedirect("mainPage");
    }
}
