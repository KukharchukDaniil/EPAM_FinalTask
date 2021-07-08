package com.epam.command;

import com.epam.entities.Course;
import com.epam.entities.CourseCategory;
import com.epam.exceptions.ServiceException;
import com.epam.service.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateCourseCommand implements Command {
    private CourseService courseService;


    public CreateCourseCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String courseName = request.getParameter("courseName");
        String courseCategory = request.getParameter("courseCategory");
        String courseDescription = request.getParameter("courseDescription");
        String courseImage = request.getParameter("courseImage");
        Course course = new Course(courseName, courseDescription, CourseCategory.valueOf(courseCategory), courseImage);
        courseService.saveCourse(course);
        return CommandResult.redirect(CommandTypes.SHOW_MAIN_PAGE);
    }
}
