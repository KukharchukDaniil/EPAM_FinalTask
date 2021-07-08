package com.epam.filters;

import com.epam.command.CommandTypes;
import com.epam.command.Destination;
import com.epam.entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RoleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        String command = request.getParameter("command");
        if (user != null && command!=null) {
            boolean isInvalidAccess = false;
            switch (user.getRole()) {
                case ADMIN:
                    if (CommandTypes.CREATE_COURSE.equals(command) ||
                            CommandTypes.DELETE_COURSE.equals(command) ||
                            CommandTypes.LOGIN.equals(command) ||
                            CommandTypes.LOGIN_ERROR.equals(command) ||
                            CommandTypes.LOGOUT.equals(command) ||
                            CommandTypes.MANAGE_COURSE.equals(command) ||
                            CommandTypes.SET_LOCALE.equals(command) ||
                            CommandTypes.SHOW_COURSE.equals(command) ||
                            CommandTypes.SHOW_CREATE_COURSE.equals(command) ||
                            CommandTypes.SHOW_ERROR.equals(command) ||
                            CommandTypes.SHOW_MAIN_PAGE.equals(command) ||
                            CommandTypes.SHOW_MANAGE_COURSE.equals(command) ||
                            CommandTypes.SIGN_UP.equals(command)) {
                    } else {
                        isInvalidAccess = true;
                    }
                    break;
                case TEACHER:
                    if (CommandTypes.ADD_TASK.equals(command) ||
                            CommandTypes.DELETE_TASK.equals(command) ||
                            CommandTypes.GRADE_SOLUTION.equals(command) ||
                            CommandTypes.LOGIN.equals(command) ||
                            CommandTypes.LOGIN_ERROR.equals(command) ||
                            CommandTypes.LOGOUT.equals(command) ||
                            CommandTypes.SET_LOCALE.equals(command) ||
                            CommandTypes.SHOW_COURSE.equals(command) ||
                            CommandTypes.SHOW_ERROR.equals(command) ||
                            CommandTypes.SHOW_MAIN_PAGE.equals(command) ||
                            CommandTypes.SHOW_MY_COURSES_PAGE.equals(command) ||
                            CommandTypes.SIGN_UP.equals(command) ||
                            CommandTypes.SHOW_SOLUTIONS.equals(command)) {
                    } else {
                        isInvalidAccess = true;
                    }
                    break;
                case STUDENT:
                    if (
                            CommandTypes.ENROLL.equals(command) ||
                                    CommandTypes.SEND_SOLUTION.equals(command) ||
                                    CommandTypes.LOGIN.equals(command) ||
                                    CommandTypes.LOGIN_ERROR.equals(command) ||
                                    CommandTypes.LOGOUT.equals(command) ||
                                    CommandTypes.SET_LOCALE.equals(command) ||
                                    CommandTypes.SHOW_COURSE.equals(command) ||
                                    CommandTypes.SHOW_ERROR.equals(command) ||
                                    CommandTypes.SHOW_MAIN_PAGE.equals(command) ||
                                    CommandTypes.SHOW_MY_COURSES_PAGE.equals(command) ||
                                    CommandTypes.SIGN_UP.equals(command)) {
                    } else {
                        isInvalidAccess = true;
                    }
                    break;

            }
            if(isInvalidAccess) {
                request.setAttribute("command", "showError");
                request.setAttribute("errorMessage", "Invalid access");
                request.getRequestDispatcher(Destination.ERROR_PAGE).forward(request,response);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
