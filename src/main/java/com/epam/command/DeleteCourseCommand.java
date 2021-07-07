package com.epam.command;

import com.epam.exceptions.ServiceException;
import com.epam.service.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCourseCommand implements Command{
    private CourseService courseService;

    public DeleteCourseCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String courseId = request.getParameter("courseId");
        courseService.deleteById(Long.valueOf(courseId));
        return CommandResult.redirect(CommandTypes.SHOW_MAIN_PAGE);
    }
}
