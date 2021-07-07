package com.epam.entities;

import java.util.Objects;

public class Course implements Entity{
    private  Long id = null;
    private final String courseName;
    private final String description;
    private final CourseCategory category;

    public static final String ID = "id";
    public static final String COURSE_NAME = "course_name";
    public static final String DESCRIPTION = "course_description";
    public static final String CATEGORY = "course_category";

    public Course(long id, String courseName, String description, CourseCategory category) {
        this.id = id;
        this.courseName = courseName;
        this.description = description;
        this.category = category;
    }

    public Course(String courseName, String courseDescription, CourseCategory courseCategory) {
        this.courseName = courseName;
        this.description = courseDescription;
        this.category = courseCategory;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDescription() {
        return description;
    }

    public CourseCategory getCategory() {
        return category;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && Objects.equals(courseName, course.courseName) && Objects.equals(description, course.description) && category == course.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseName, description, category);
    }
}
