package com.epam.command;

import com.epam.entities.Course;
import com.epam.entities.User;
import com.epam.exceptions.ServiceException;
import com.epam.service.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowMyCoursesPage implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        CourseService courseService = new CourseService();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer totalItems = courseService.countMyCourses(user.getId());

        String pageIndexString = request.getParameter("pageIndex");


        Integer pageIndex = pageIndexString!=null? Integer.parseInt(pageIndexString):1;
        List<Course> myCoursesByPage = courseService.getMyCoursesByPage(user.getId(), pageIndex);
        request.setAttribute("coursesList", myCoursesByPage);
        request.setAttribute("totalItems", totalItems);
        return CommandResult.forward(Destination.MY_COURSES_PAGE);
    }
}
