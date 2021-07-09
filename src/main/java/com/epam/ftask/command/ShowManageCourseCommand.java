package com.epam.ftask.command;

import com.epam.ftask.entities.Course;
import com.epam.ftask.entities.User;
import com.epam.ftask.entities.UserRole;
import com.epam.ftask.exceptions.ServiceException;
import com.epam.ftask.service.CourseService;
import com.epam.ftask.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowManageCourseCommand implements Command{
    private static final String PAGE_INDEX = "pageIndex";
    private static final String COURSE_ID = "courseId";
    private static final String CHECKBOXES_TO_CHECK = "checkboxesToCheck";
    private static final String COURSE_NAME = "courseName";
    private static final String COURSE_DESCRIPTION = "courseDescription";
    private static final String USERS_LIST = "usersList";
    private static final String TOTAL_ITEMS = "totalItems";
    private UserService userService;
    private CourseService courseService;

    public ShowManageCourseCommand(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        String pageIndexString = request.getParameter(PAGE_INDEX);
        Integer totalItems = userService.countUsersByRole(UserRole.TEACHER);
        Integer pageIndex = pageIndexString!=null? Integer.parseInt(pageIndexString):1;
        List<User> usersList = userService.findUsersByRoleAndPage(UserRole.TEACHER,pageIndex);

        Long courseId = Long.valueOf(request.getParameter(COURSE_ID));
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : usersList) {
            Long userId = user.getId();
            if(courseService.isUserEnrolled(userId,courseId)){
                stringBuilder.append(userId+",");
            }
        }
        Course course = courseService.getCourseById(courseId);
        request.setAttribute(CHECKBOXES_TO_CHECK,stringBuilder.toString());
        request.setAttribute(COURSE_NAME,course.getCourseName());
        request.setAttribute(COURSE_DESCRIPTION,course.getDescription());
        request.setAttribute(USERS_LIST, usersList);
        request.setAttribute(TOTAL_ITEMS, totalItems);
        return CommandResult.forward(Destination.MANAGE_COURSE);
    }
}
