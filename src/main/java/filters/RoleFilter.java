package filters;

import DTO.UserDTO;
import utils.roles.Roles;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static utils.constant.ConstantsContainer.*;

@WebFilter(filterName = "/RoleFilter", urlPatterns = {"/mainPageAdmin"})
public class RoleFilter implements Filter {
    private final Logger LOGGER = Logger.getLogger(RoleFilter.class.getName());

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
            LOGGER.log(Level.WARNING, VERIFICATION_FAILED + VERIFICATION_FAILED_SESSION);
            res.sendRedirect(contextPath + "/signUp.jsp");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute(CURRENT_MSG);
        if (user == null) {
            LOGGER.log(Level.INFO, VERIFICATION_FAILED + VERIFICATION_FAILED_USER);
            res.sendRedirect(contextPath + "/signUp.jsp");
            return;
        }

        Roles role = user.getRole();
        if (role != Roles.ADMIN) {
            LOGGER.log(Level.INFO, VERIFICATION_FAILED + VERIFICATION_FAILED_VALIDATE);
            res.sendRedirect(contextPath + "/signUp.jsp");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}