package com.epam.command;

import com.epam.entities.Course;
import com.epam.entities.User;
import com.epam.entities.UserRole;
import com.epam.exceptions.ServiceException;
import com.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCreateCoursePage implements Command{
    private UserService userService;
    public ShowCreateCoursePage(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        return CommandResult.forward(Destination.CREATE_COURSE);
    }
}
