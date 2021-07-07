package com.epam.command;

import com.epam.service.CourseService;
import com.epam.service.SolutionService;
import com.epam.service.TaskService;
import com.epam.service.UserService;

import static com.epam.command.CommandTypes.*;

public class CommandFactory {

    public CommandFactory() {
    }

    public Command create(String commandName) throws IllegalArgumentException {
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
                return new ShowPageCommand(Destination.LOGIN_PAGE.getPageAddress());
            case SHOW_REGISTRATION_PAGE:
                return new ShowPageCommand(Destination.REGISTRATION_PAGE.getPageAddress());
            case SHOW_COURSE:
                return new ShowCoursePage(new CourseService(), new TaskService(), new SolutionService());
            case ENROLL:
                return new EnrollCommand(new UserService());
            case SEND_SOLUTION:
                return new SendSolutionCommand(new SolutionService());
            case SHOW_CREATE_COURSE:
                return new ShowPageCommand(Destination.CREATE_COURSE);
            case CREATE_COURSE:
                return new CreateCourseCommand(new CourseService());
            case SHOW_MANAGE_COURSE:
                return new ShowManageCourseCommand(new UserService(), new CourseService());
            case MANAGE_COURSE:
                return new ManageCourseCommand(new CourseService(), new UserService());
            case DELETE_COURSE:
                return new DeleteCourseCommand(new CourseService());
            case SHOW_ERROR:
                return new ShowErrorCommand();
            default:
                throw new IllegalArgumentException("Wrong command name");
        }
    }
}
