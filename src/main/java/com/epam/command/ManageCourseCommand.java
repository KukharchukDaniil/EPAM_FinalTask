package com.epam.command;

import com.epam.entities.Course;
import com.epam.entities.CourseCategory;
import com.epam.exceptions.ServiceException;
import com.epam.service.CourseService;
import com.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ManageCourseCommand implements Command {
    private CourseService courseService;
    private UserService userService;

    public ManageCourseCommand(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String courseName = request.getParameter("courseName");
        String courseDescription = request.getParameter("courseDescription");
        String courseCategory = request.getParameter("courseCategory");
        Long courseId = Long.parseLong(request.getParameter("courseId"));
        Course course = new Course(courseId, courseName,
                courseDescription, CourseCategory.valueOf(courseCategory), null);
        courseService.saveCourse(course);
        String teachersListToEnroll = request.getParameter("teachersListToEnroll");
        String teachersListToRemove = request.getParameter("teachersListToRemove");
        System.out.println(teachersListToRemove);
        if (!teachersListToEnroll.isEmpty()) {

            List<Long> teachersList = Arrays.stream(teachersListToEnroll.split(",")).filter(s -> {
                return !s.isEmpty();
            }).map(Long::parseLong).collect(Collectors.toList());
            for (Long teacherId : teachersList) {
                if (!courseService.isUserEnrolled(teacherId, courseId)) {
                    userService.enrollOnCourse(courseId, teacherId);
                }
            }
        }
        if (!teachersListToRemove.isEmpty()) {
            List<Long> teachersList = Arrays.stream(teachersListToRemove.split(",")).filter(s -> {
                return !s.isEmpty();
            }).map(Long::parseLong).collect(Collectors.toList());
            for (Long teacherId : teachersList) {
                if (courseService.isUserEnrolled(teacherId, courseId)) {
                    userService.removeUserFromCourse(teacherId, courseId);
                }
            }
        }
        return CommandResult.redirect(CommandTypes.SHOW_MAIN_PAGE);
    }
}
