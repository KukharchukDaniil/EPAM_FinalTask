package com.epam.command;

import com.epam.entities.Course;
import com.epam.entities.CourseCategory;
import com.epam.exceptions.ServiceException;
import com.epam.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

public class CreateCourseCommand implements Command {
    private CourseService courseService;


    public CreateCourseCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String courseName = InjectionProtector.getSafeAttribute(request,"courseName");
        String courseCategory = request.getParameter("courseCategory");
        String courseDescription = InjectionProtector.getSafeAttribute(request,"courseDescription");

        Course course = new Course(courseName, courseDescription, CourseCategory.valueOf(courseCategory));
        courseService.saveCourse(course);
        return CommandResult.redirect(CommandTypes.SHOW_MAIN_PAGE);
    }

}
