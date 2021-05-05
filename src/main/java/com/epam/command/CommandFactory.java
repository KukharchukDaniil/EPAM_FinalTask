package com.epam.command;

import com.epam.exceptions.DaoException;
import com.epam.service.SolutionService;
import com.epam.service.UserService;

public class CommandFactory {
    public static final String LOGIN = "login";
    public static final String SHOW_MAIN_PAGE = "mainPage";
    public static final String SHOW_LOGIN_PAGE = "loginPage";
    public static final String SHOW_MY_COURSES_PAGE = "myCourses";
    public static final String SHOW_COURSE = "showCourse";
    public static final String ENROLL = "enroll";
    public static final String LOGOUT = "logout";
    public static final String SEND_SOLUTION = "sendSolution";

    public CommandFactory() {
    }

    public Command create(String commandName) throws IllegalArgumentException, DaoException {
        switch (commandName) {
            case LOGIN:
                return new LoginCommand(new UserService());
            case SHOW_MAIN_PAGE:
                return new ShowPageCommand(Pages.MAIN_PAGE);
            case SHOW_MY_COURSES_PAGE:
                return new ShowPageCommand(Pages.MY_COURSES_PAGE);
            case SHOW_LOGIN_PAGE:
                return new ShowPageCommand(Pages.LOGIN_PAGE);
            case SHOW_COURSE:
                return new ShowPageCommand(Pages.COURSE_PAGE);
            case ENROLL:
                return new EnrollCommand(new UserService());
            case SEND_SOLUTION:
                return new SendSolutionCommand(new SolutionService());
            default:
                throw new IllegalArgumentException("Wrong command name");
        }
    }
}
