package com.epam.command;

import com.epam.entities.User;
import com.epam.exceptions.ServiceException;
import com.epam.validator.PasswordValidator;
import com.epam.validator.UsernameValidator;
import com.epam.service.UserService;
import com.epam.validator.NameValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        UserService userService = new UserService();

        Boolean isUsernameUnique = userService.isUsernameUnique(username);


        UsernameValidator usernameValidator = new UsernameValidator();
        NameValidator nameValidator = new NameValidator();
        PasswordValidator passwordValidator = new PasswordValidator();

        boolean isLoginValid = usernameValidator.validate(username);
        boolean isPasswordValid = passwordValidator.validate(password);
        boolean isNameValid = nameValidator.validate(name);

        if (!isLoginValid || !isPasswordValid || !isNameValid || !isUsernameUnique) {
            if (!isLoginValid) {
                request.setAttribute("usernameError", true);
            }
            if(!isUsernameUnique){
                request.setAttribute("usernameIsTaken",true);
            }
            if (!isNameValid) {
                request.setAttribute("nameError", true);
            }
            if (!isPasswordValid) {
                request.setAttribute("passwordError", true);
            }
            return CommandResult.forward(Destination.REGISTRATION_PAGE.getPageAddress());
        }


        if (userService.doesUserExist(username)) {
            request.setAttribute("usernameIsTaken", true);
            return CommandResult.forward(Destination.REGISTRATION_PAGE.getPageAddress());
        } else {
            User user = new User(name, username, password);
            userService.signUpUser(user);
        }
        return CommandResult.redirect(CommandTypes.SHOW_LOGIN_PAGE);
    }
}
