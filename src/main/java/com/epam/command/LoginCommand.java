package com.epam.command;


import com.epam.entities.User;
import com.epam.exceptions.ServiceException;
import com.epam.service.UserService;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {
    private final UserService userService;
    private static final String PAGE = "pageIndex";
    private static final Integer DEFAULT_PAGE = 1;
    private static final String USERNAME = "login";
    private static final String PASSWORD = "password";
    private static final String USER = "user";
    public LoginCommand(UserService service) {
        this.userService = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession(true);
        String username = request.getParameter(USERNAME);
        String password = request.getParameter(PASSWORD);
        Optional<User> optionalUser = null;
        optionalUser = userService.login(username, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            session.setAttribute(USER, user);
            request.setAttribute(PAGE,DEFAULT_PAGE);
            response.addCookie(new Cookie(USERNAME, username));
            response.addCookie(new Cookie(PASSWORD, password));
            return CommandResult.redirect(CommandFactory.SHOW_MAIN_PAGE);
        } else {
            return CommandResult.redirect(CommandFactory.LOGIN_ERROR);
        }
//        else {
//            request.setAttribute("errorMassage", "Invalid name of password");
//        }

    }
}
