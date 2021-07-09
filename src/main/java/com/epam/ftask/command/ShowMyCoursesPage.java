package com.epam.ftask.command;

import com.epam.ftask.entities.Course;
import com.epam.ftask.entities.User;
import com.epam.ftask.exceptions.ServiceException;
import com.epam.ftask.service.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowMyCoursesPage implements Command {

    private static final String USER = "user";
    private static final String PAGE_INDEX = "pageIndex";
    private static final String COURSES_LIST = "coursesList";
    private static final String TOTAL_ITEMS = "totalItems";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        CourseService courseService = new CourseService();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        Integer totalItems = courseService.countMyCourses(user.getId());

        String pageIndexString = request.getParameter(PAGE_INDEX);


        Integer pageIndex = pageIndexString!=null? Integer.parseInt(pageIndexString):1;
        List<Course> myCoursesByPage = courseService.getMyCoursesByPage(user.getId(), pageIndex);
        request.setAttribute(COURSES_LIST, myCoursesByPage);
        request.setAttribute(TOTAL_ITEMS, totalItems);
        return CommandResult.forward(Destination.MY_COURSES_PAGE);
    }
}
