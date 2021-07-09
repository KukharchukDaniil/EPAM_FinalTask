package com.epam.ftask.command;

import com.epam.ftask.entities.User;
import com.epam.ftask.exceptions.ServiceException;
import com.epam.ftask.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EnrollCommand implements Command {
    private static final String COURSE_ID = "courseId";
    private static final String COURSE_ID_PARAMETER = "&courseId=";
    private UserService userService;
    private static final String USER = "user";

    public EnrollCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        Integer courseId = Integer.valueOf(request.getParameter(COURSE_ID));
        userService.enrollOnCourse(courseId, user.getId());
        return CommandResult.redirect(CommandTypes.SHOW_COURSE, COURSE_ID_PARAMETER, String.valueOf(courseId));
    }
}
