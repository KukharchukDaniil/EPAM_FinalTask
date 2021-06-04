package com.epam.command;

import com.epam.entities.User;
import com.epam.entities.UserRole;
import com.epam.exceptions.ServiceException;
import com.epam.service.UserService;
import com.epam.validator.NameValidator;
import com.epam.validator.PasswordValidator;
import com.epam.validator.UsernameValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        UserService userService = new UserService();

        UsernameValidator usernameValidator = new UsernameValidator();
        NameValidator nameValidator = new NameValidator();
        PasswordValidator passwordValidator = new PasswordValidator();

        boolean isLoginValid = usernameValidator.validate(username);
        boolean isPasswordValid = passwordValidator.validate(password);
        boolean isNameValid = nameValidator.validate(name);

        if (!isLoginValid || !isPasswordValid || !isNameValid) {
            if (!isLoginValid) {
                request.setAttribute("usernameError", true);
            }
            if (!isNameValid) {
                request.setAttribute("nameError", true);
            }
            if (!isPasswordValid) {
                request.setAttribute("passwordError", true);
            }
            return CommandResult.forward(Pages.REGISTRATION_PAGE);
        }


        if (userService.doesUserExist(username)) {
            request.setAttribute("usernameIsTaken", true);
            return CommandResult.forward(Pages.REGISTRATION_PAGE);
        } else {
            User user = new User(name, username, password);
            userService.signUpUser(user);
        }
        return CommandResult.redirect(CommandFactory.SHOW_LOGIN_PAGE);
    }
}
