package com.epam.ftask.command;

import com.epam.ftask.entities.Course;
import com.epam.ftask.entities.CourseCategory;
import com.epam.ftask.exceptions.ServiceException;
import com.epam.ftask.service.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
