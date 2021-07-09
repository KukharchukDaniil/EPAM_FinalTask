package com.epam.ftask.command;

import com.epam.ftask.entities.User;
import com.epam.ftask.exceptions.ServiceException;
import com.epam.ftask.validator.PasswordValidator;
import com.epam.ftask.validator.UsernameValidator;
import com.epam.ftask.service.UserService;
import com.epam.ftask.validator.NameValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpCommand implements Command {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String USERNAME_ERROR = "usernameError";
    private static final String USERNAME_IS_TAKEN = "usernameIsTaken";
    private static final String NAME_ERROR = "nameError";
    private static final String PASSWORD_ERROR = "passwordError";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String username =InjectionProtector.getSafeAttribute(request, USERNAME);
        String password = InjectionProtector.getSafeAttribute(request, PASSWORD);
        String name = InjectionProtector.getSafeAttribute(request, NAME);
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
                request.setAttribute(USERNAME_ERROR, true);
            }
            if(!isUsernameUnique){
                request.setAttribute(USERNAME_IS_TAKEN,true);
            }
            if (!isNameValid) {
                request.setAttribute(NAME_ERROR, true);
            }
            if (!isPasswordValid) {
                request.setAttribute(PASSWORD_ERROR, true);
            }
            return CommandResult.forward(Destination.REGISTRATION_PAGE);
        }


        if (userService.doesUserExist(username)) {
            request.setAttribute(USERNAME_IS_TAKEN, true);
            return CommandResult.forward(Destination.REGISTRATION_PAGE);
        } else {
            User user = new User(name, username, password);
            userService.signUpUser(user);
        }
        return CommandResult.redirect(CommandTypes.SHOW_LOGIN_PAGE);
    }
}
