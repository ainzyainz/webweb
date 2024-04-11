package filters;

import DTO.UserDTO;
import entities.User;
import utils.roles.Roles;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebFilter(filterName = "/AuthorizationFilter", urlPatterns = {
        "/mainPage", "/library",
        "/currentGame","/currentCatalog",
        "/balance","/balance/addFive","/balance/addTen",
        "/balance/addTwenty","/balance/addFifty",
        "/balance/addGrand","/mainPageAdmin"})
public class AuthorizationFilter implements Filter {
    Logger LOGGER = Logger.getLogger(AuthorizationFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String contextPath = req.getContextPath();
        HttpSession session = req.getSession();

        if (session == null) {
            LOGGER.log(Level.WARNING,"Failed authentication filter. No active session");
            res.sendRedirect(contextPath + "/signUp.jsp");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("current");
        if (user == null) {
            LOGGER.log(Level.INFO,"Failed authentication filter. No active user");
            res.sendRedirect(contextPath + "/signUp.jsp");
            return;
        }

        Roles role = user.getRole();
        if (role == null) {
            LOGGER.log(Level.INFO,"Failed authentication filter. Role is null");
            res.sendRedirect(contextPath + "/signUp.jsp");
            return;
        }

        if (user.getPassword().equalsIgnoreCase(session.getAttribute("password").toString()) &&
                user.getEmail().equalsIgnoreCase(session.getAttribute("email").toString())) {
            req.setAttribute("current",user);
            chain.doFilter(request, response);
        } else {
            LOGGER.log(Level.INFO,"Failed authentication filter. Wrong credentials");
            res.sendRedirect(contextPath + "/signUp.jsp");
        }
    }

    @Override
    public void destroy() {
    }
}