package com.epam.command;

import com.epam.exceptions.DaoException;
import com.epam.service.SolutionService;
import com.epam.service.UserService;

public class CommandFactory {
    public static final String LOGIN = "login";
    public static final String LOGIN_ERROR = "loginError";
    public static final String SHOW_MAIN_PAGE = "mainPage";
    public static final String SHOW_REGISTRATION_PAGE = "registrationPage";
    public static final String SHOW_LOGIN_PAGE = "loginPage";
    public static final String SHOW_MY_COURSES_PAGE = "myCourses";
    public static final String SHOW_COURSE = "showCourse";
    public static final String ENROLL = "enroll";
    public static final String LOGOUT = "logout";
    public static final String SIGN_UP = "signUp";
    public static final String SEND_SOLUTION = "sendSolution";
    public static final String DELETE_TASK = "deleteTask";
    public static final String SHOW_SOLUTIONS = "showSolutions";
    public static final String GRADE_SOLUTION = "gradeSolution";
    public static final String ADD_TASK = "addTask";
    public static final String SET_LOCALE = "setLocale";

    public CommandFactory() {
    }

    public Command create(String commandName) throws IllegalArgumentException, DaoException {
        switch (commandName) {
            case SET_LOCALE:
                return new SetLocaleCommand();
            case ADD_TASK:
                return new AddTaskCommand();
            case GRADE_SOLUTION:
                return new GradeCommand();
            case DELETE_TASK:
                return new DeleteTaskCommand();
            case SHOW_SOLUTIONS:
                return new ShowSolutionsPage();
            case LOGOUT:
                return new LogoutCommand();
            case SIGN_UP:
                return new SignUpCommand();
            case LOGIN:
                return new LoginCommand(new UserService());
            case LOGIN_ERROR:
                return new LoginErrorCommand();
            case SHOW_MAIN_PAGE:
                return new ShowMainPage();
            case SHOW_MY_COURSES_PAGE:
                return new ShowMyCoursesPage();
            case SHOW_LOGIN_PAGE:
                return new ShowPageCommand(Pages.LOGIN_PAGE);
            case SHOW_REGISTRATION_PAGE:
                return new ShowPageCommand(Pages.REGISTRATION_PAGE);
            case SHOW_COURSE:
                return new ShowCoursePage();
            case ENROLL:
                return new EnrollCommand(new UserService());
            case SEND_SOLUTION:
                return new SendSolutionCommand(new SolutionService());
            default:
                throw new IllegalArgumentException("Wrong command name");
        }
    }
}
