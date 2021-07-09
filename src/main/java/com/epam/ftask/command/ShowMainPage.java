package com.epam.ftask.command;

import com.epam.ftask.entities.Course;
import com.epam.ftask.exceptions.ServiceException;
import com.epam.ftask.service.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowMainPage implements Command {

    private static final String PAGE_INDEX = "pageIndex";
    private static final String COURSES_LIST = "coursesList";
    private static final String TOTAL_ITEMS = "totalItems";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        CourseService courseService = new CourseService();
        Integer totalItems = courseService.countAllCourses();
        String pageIndexString = request.getParameter(PAGE_INDEX);
        Integer pageIndex = pageIndexString != null ? Integer.parseInt(pageIndexString) : 1;
        List<Course> allByPage = courseService.getAllByPage(pageIndex);
        request.setAttribute(COURSES_LIST, allByPage);
        request.setAttribute(TOTAL_ITEMS, totalItems);

        return CommandResult.forward(Destination.MAIN_PAGE);
    }
}
