package com.epam.dao;

import com.epam.entities.Course;
import com.epam.exceptions.DaoException;
import com.epam.mapper.CourseRowMapper;
import com.epam.mapper.RowMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.List;

public class CourseDao extends AbstractDao<Course> {
    private static final String TABLE_NAME = "COURSES";
    private static final String FIND_BY_ID = "SELECT * FROM COURSES WHERE ID = ?";
    private static final String GET_ALL_BY_PAGE = "SELECT * FROM COURSES LIMIT ?,5";
    private static final String COUNT_COURSES = "SELECT COUNT(*) FROM COURSES";
    private static final String FIND_BY_USER_ID = "SELECT * FROM courses_db.courses WHERE id IN(SELECT course_id FROM courses_db.users_courses WHERE user_id = ?) ";
    private static final String FIND_BY_USER_ID_AND_PAGE = "SELECT * FROM courses_db.courses WHERE id IN(SELECT course_id FROM courses_db.users_courses WHERE user_id = ?) LIMIT ?,5";
    private static final String INSERT_COURSE = "INSERT INTO COURSES(COURSE_NAME,COURSE_DESCRIPTION,COURSE_CATEGORY) VALUES(?, ?, ?)";
    private static final String UPDATE_COURSE_NAME_AND_DESCRIPTION_AND_CATEGORY = "UPDATE COURSES SET COURSE_NAME = ?, COURSE_DESCRIPTION = ?, COURSE_CATEGORY = ? WHERE ID = ? ";
    private static final String COUNT_COURSES_BY_ID = "SELECT COUNT(*) FROM users_courses WHERE user_id = %s";
    private static final String CHECK_IF_USER_IS_ENROLLED = "SELECT * FROM users_courses WHERE user_id = ? and course_id = ?";

    public CourseDao(Connection connection, CourseRowMapper courseRowMapper) {
        super(connection, courseRowMapper);
    }

    public List<Course> getCoursesByUserIdAndPage(long userId, int pageIndex) throws DaoException {
        return executeQuery(FIND_BY_USER_ID_AND_PAGE, rowMapper, userId, (pageIndex - 1) * 5);
    }

    public List<Course> getCoursesByUserId(long userId) throws DaoException {
        return executeQuery(FIND_BY_USER_ID, rowMapper, userId);
    }

    public Course getCourseById(long courseId) throws DaoException {
        Optional<Course> result = getById(courseId);
        if (!result.isPresent()) {
            throw new DaoException("No course with this ID");
        }
        return result.get();
    }

    public Integer countCoursesByUserId(long id) throws DaoException {
        String query = String.format(COUNT_COURSES_BY_ID, id);
        return countQuery(query);
    }

    public Boolean isUserEnrolled(long userId, long courseId) throws DaoException {
        RowMapper<Course> mapper = new RowMapper<Course>() {
            @Override
            public Course map(ResultSet resultSet) throws SQLException {
                return new Course(resultSet.getLong(1),null,null,null);
            }
        };
        List<Course> resultList = executeQuery(CHECK_IF_USER_IS_ENROLLED, mapper, userId, courseId);
        return !resultList.isEmpty();
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected RowMapper getRowMapper() {
        return rowMapper;
    }

    @Override
    protected void update(Course entity) throws DaoException {
        Optional<Course> course = getById(entity.getId());
        if (!course.isPresent()) {
            throw new DaoException("Invalid course id! Can't update course.");
        }
        executeUpdate(
                UPDATE_COURSE_NAME_AND_DESCRIPTION_AND_CATEGORY,
                entity.getCourseName(),
                entity.getDescription(),
                entity.getCategory().toString(),
                entity.getId()
        );
    }

    @Override
    protected void create(Course entity) throws DaoException {
        executeUpdate(
                INSERT_COURSE,
                entity.getCourseName(),
                entity.getDescription(),
                entity.getCategory().toString());
    }
}
