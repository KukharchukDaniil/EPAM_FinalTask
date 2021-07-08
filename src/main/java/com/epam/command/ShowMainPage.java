package com.epam.command;

import com.epam.entities.Course;
import com.epam.exceptions.ServiceException;
import com.epam.service.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowMainPage implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        CourseService courseService = new CourseService();
        Integer totalItems = courseService.countAllCourses();
        String pageIndexString = request.getParameter("pageIndex");
        Integer pageIndex = pageIndexString!=null? Integer.parseInt(pageIndexString):1;
        List<Course> allByPage = courseService.getAllByPage(pageIndex);
        request.setAttribute("coursesList", allByPage);
        request.setAttribute("totalItems", totalItems);

        return CommandResult.forward(Destination.MAIN_PAGE);
    }
}
