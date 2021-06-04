package com.epam.filters;

import com.epam.command.CommandFactory;
import com.epam.entities.User;
import com.epam.exceptions.ServiceException;
import com.epam.service.UserService;
import sun.security.ssl.CookieExtension;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AuthorisationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);
        String command = request.getParameter("command");
        if (session.getAttribute("user") == null && command != null && !command.equals("login")
                && !command.equals("loginPage") && !command.equals("loginError") && !command.equals("registrationPage")
                && !command.equals("signUp") &&!command.equals("setLocale")) {
            response.sendRedirect(request.getContextPath() + "/controller?command=" + CommandFactory.SHOW_LOGIN_PAGE);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
