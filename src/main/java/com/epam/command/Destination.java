package com.epam.command;

public enum Destination {
    MAIN_PAGE("WEB-INF/view/main.jsp"),
    MY_COURSES_PAGE("WEB-INF/view/courses.jsp"),
    LOGIN_PAGE("index.jsp"),
    COURSE_PAGE("WEB-INF/view/course.jsp"),
    REGISTRATION_PAGE("WEB-INF/view/registration.jsp"),
    SOLUTIONS_PAGE("WEB-INF/view/solutions.jsp");
    public static final String CREATE_COURSE = "WEB-INF/view/createCourse.jsp";
    public static final String MANAGE_COURSE = "WEB-INF/view/manageCourse.jsp";
    public static final String ERROR_PAGE = "WEB-INF/view/error.jsp";
    private String pageAddress;

    Destination(String value) {
        pageAddress = value;
    }

    public String getPageAddress() {
        return pageAddress;
    }
}
