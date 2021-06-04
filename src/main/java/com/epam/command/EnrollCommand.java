package com.epam.command;

import com.epam.entities.User;
import com.epam.exceptions.ServiceException;
import com.epam.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EnrollCommand implements Command{
    private UserService userService;
    public EnrollCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer courseId = Integer.valueOf(request.getParameter("courseId"));
        if(user == null){
            throw new IllegalArgumentException("Unauthorized access!!!");
        }
        userService.enrollOnCourse(courseId,user.getId());
        return CommandResult.redirect(CommandFactory.SHOW_COURSE,"&courseId=", String.valueOf(courseId));
    }
}
