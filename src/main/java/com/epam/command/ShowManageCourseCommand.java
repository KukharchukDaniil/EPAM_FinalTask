package com.epam.command;

import com.epam.entities.Course;
import com.epam.entities.User;
import com.epam.entities.UserRole;
import com.epam.exceptions.ServiceException;
import com.epam.service.CourseService;
import com.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.StringJoiner;

public class ShowManageCourseCommand implements Command{
    private UserService userService;
    private CourseService courseService;

    public ShowManageCourseCommand(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        String pageIndexString = request.getParameter("pageIndex");
        Integer totalItems = userService.countUsersByRole(UserRole.TEACHER);
        Integer pageIndex = pageIndexString!=null? Integer.parseInt(pageIndexString):1;
        List<User> usersList = userService.findUsersByRoleAndPage(UserRole.TEACHER,pageIndex);

        Long courseId = Long.valueOf(request.getParameter("courseId"));
//        String enrolledTeachers = new String();
//        StringJoiner stringJoiner = new StringJoiner(resultString);
        
        Course course = courseService.getCourseById(courseId);
        request.setAttribute("courseName",course.getCourseName());
        request.setAttribute("courseDescription",course.getDescription());
        request.setAttribute("usersList", usersList);
        request.setAttribute("totalItems", totalItems);
        return CommandResult.forward(Destination.MANAGE_COURSE);
    }
}
