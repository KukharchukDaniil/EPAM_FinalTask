package com.epam.filters;

import com.epam.command.CommandFactory;
import com.epam.command.CommandTypes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorisationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);
        String command = request.getParameter("command");
        if (session.getAttribute("user") == null && command != null && !"login".equals(command)
                && !"loginPage".equals(command) && !"loginError".equals(command) && !"registrationPage".equals(command)
                && !"signUp".equals(command) &&!"setLocale".equals(command)) {
            response.sendRedirect(request.getContextPath() + "/controller?command=" + CommandTypes.SHOW_LOGIN_PAGE);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
