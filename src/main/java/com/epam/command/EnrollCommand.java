package com.epam.command;

import com.epam.entities.User;
import com.epam.exceptions.ServiceException;
import com.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EnrollCommand implements Command{
    private UserService userService;
    private static final String USER = "user";
    public EnrollCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        Integer courseId = Integer.valueOf(request.getParameter("courseId"));
        userService.enrollOnCourse(courseId,user.getId());
        return CommandResult.redirect(CommandTypes.SHOW_COURSE,"&courseId=", String.valueOf(courseId));
    }
}
