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
    public static final String COURSE_NAME = "courseName";
    public static final String COURSE_DESCRIPTION = "courseDescription";
    public static final String COURSE_CATEGORY = "courseCategory";
    public static final String COURSE_ID = "courseId";
    public static final String TEACHERS_LIST_TO_ENROLL = "teachersListToEnroll";
    public static final String TEACHERS_LIST_TO_REMOVE = "teachersListToRemove";
    public static final String REGEX = ",";
    private CourseService courseService;
    private UserService userService;

    public ManageCourseCommand(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String courseName = InjectionProtector.getSafeAttribute(request, COURSE_NAME);
        String courseDescription = InjectionProtector.getSafeAttribute(request,COURSE_DESCRIPTION);
        String courseCategory = request.getParameter(COURSE_CATEGORY);
        Long courseId = Long.parseLong(request.getParameter(COURSE_ID));
        Course course = new Course(courseId, courseName,
                courseDescription, CourseCategory.valueOf(courseCategory));
        courseService.saveCourse(course);
        String teachersListToEnroll = request.getParameter(TEACHERS_LIST_TO_ENROLL);
        String teachersListToRemove = request.getParameter(TEACHERS_LIST_TO_REMOVE);
        System.out.println(teachersListToRemove);
        if (!teachersListToEnroll.isEmpty()) {

            List<Long> teachersList = Arrays.stream(teachersListToEnroll.split(REGEX)).filter(s -> {
                return !s.isEmpty();
            }).map(Long::parseLong).collect(Collectors.toList());
            for (Long teacherId : teachersList) {
                if (!courseService.isUserEnrolled(teacherId, courseId)) {
                    userService.enrollOnCourse(courseId, teacherId);
                }
            }
        }
        if (!teachersListToRemove.isEmpty()) {
            List<Long> teachersList = Arrays.stream(teachersListToRemove.split(REGEX)).filter(s -> {
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
