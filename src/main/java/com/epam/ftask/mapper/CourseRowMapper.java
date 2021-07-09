package com.epam.ftask.mapper;

import com.epam.ftask.entities.Course;
import com.epam.ftask.entities.CourseCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class CourseRowMapper implements RowMapper<Course> {
    @Override
    public Course map(ResultSet resultSet) throws SQLException {
        Course course = new Course(
                resultSet.getLong(Course.ID),
                resultSet.getString(Course.COURSE_NAME),
                resultSet.getString(Course.DESCRIPTION),
                CourseCategory.valueOf(resultSet.getString(Course.CATEGORY).toUpperCase(Locale.ROOT)));
        return course;
    }
}
