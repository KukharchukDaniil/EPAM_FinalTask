package com.epam.filters;

import com.epam.entities.User;
import com.epam.exceptions.ServiceException;
import com.epam.service.UserService;

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

public class AuthorisationController implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);
        String command = request.getParameter("command");
        if (session.getAttribute("user") == null && command!=null && !command.equals("login")) {
            Cookie[] cookies = request.getCookies();
            Optional<Cookie> usernameCookie = Arrays.stream(cookies).filter(new Predicate<Cookie>() {
                @Override
                public boolean test(Cookie cookie) {
                    return cookie.getName().equals("username");
                }
            }).findAny();
            Optional<Cookie> passwordCookie = Arrays.stream(cookies).filter(new Predicate<Cookie>() {
                @Override
                public boolean test(Cookie cookie) {
                    return cookie.getName().equals("password");
                }
            }).findAny();

            try {
                if (usernameCookie.isPresent() && passwordCookie.isPresent()) {
                    UserService service = new UserService();
                    Optional<User> userOptional = null;
                    userOptional = service.login(usernameCookie.get().getValue(), passwordCookie.get().getValue());
                    if (userOptional.isPresent()) {
                        session.setAttribute("user", userOptional.get());
                        session.setAttribute("pageIndex",1);
                    }

                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
