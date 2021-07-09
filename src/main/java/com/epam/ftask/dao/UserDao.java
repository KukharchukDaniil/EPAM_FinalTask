package com.epam.ftask.dao;

import com.epam.ftask.entities.User;
import com.epam.ftask.entities.UserRole;
import com.epam.ftask.exceptions.DaoException;
import com.epam.ftask.mapper.UserRowMapper;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserDao extends AbstractDao<User> {
    private static final String FIND_BY_LOGIN_AND_PASSWORD = "SELECT ID, NAME, USERNAME, PASSWORD, ROLE FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
    private static final String INSERT_USER = "INSERT INTO USERS(NAME,USERNAME,PASSWORD) VALUES(?, ?, ?)";
    private static final String ENROLL_USER = "INSERT INTO USERS_COURSES(`course_id`, `user_id`) VALUES (?, ?);";
    private static final String UPDATE_USER_NAME_AND_PASSWORD = "UPDATE USERS SET NAME = ?, PASSWORD = ?, WHERE ID = ? ";
    private static final String TABLE_NAME = "USERS";
    private static final String FIND_USER_BY_USERNAME = "SELECT * FROM USERS WHERE USERNAME = ?";
    private static final String FIND_USES_BY_ROLE_AND_PAGE = "SELECT * FROM courses_db.users WHERE role = ? LIMIT ?,5";
    private static final String COUNT_USERS_BY_ROLE = "SELECT COUNT(*) FROM courses_db.users WHERE role = '%s'";
    private static final String COUNT_USERS_BY_USERNAME = "SELECT COUNT(*) FROM courses_db.users WHERE username = '%s'";
    private static final Integer ITEMS_PER_PAGE = 10;
    private static final String DELETE_USER_FROM_COURSE ="DELETE FROM courses_db.users_courses WHERE user_id = ? AND course_id = ?"; ;

    public UserDao(Connection connection, UserRowMapper userRowMapper) {
        super(connection, userRowMapper);
    }

    public Optional<User> findUserByLoginAndPassword(final String login, final String password) throws DaoException {
        return executeForSingleResult(FIND_BY_LOGIN_AND_PASSWORD, rowMapper, login, password);
    }

    public void enrollOnCourse(long courseId, long userId) throws DaoException {
        executeUpdate(ENROLL_USER, courseId, userId);
    }

    public Optional<User> findUserByUsername(String username) throws DaoException {
        return executeForSingleResult(FIND_USER_BY_USERNAME, rowMapper, username);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }


    @Override
    public void update(User entity) throws DaoException {
        Optional<User> user = getById(entity.getId());
        if (!user.isPresent()) {
            throw new DaoException("Invalid user id! Can't update user.");
        }
        executeUpdate(UPDATE_USER_NAME_AND_PASSWORD, entity.getName(), entity.getPassword());

    }

    @Override
    public void create(User entity) throws DaoException {
        executeUpdate(INSERT_USER, entity.getName(), entity.getUsername(), entity.getPassword());
    }

    public List<User> findUsersByRoleAndPage(UserRole role, Integer pageIndex) throws DaoException {
        return executeQuery(FIND_USES_BY_ROLE_AND_PAGE, rowMapper,
                role.toString(), (pageIndex - 1) * 5);
    }

    public Integer countUsersByRole(UserRole role) throws DaoException {
        String query = String.format(COUNT_USERS_BY_ROLE, role.toString().toLowerCase());
        return countQuery(query);
    }

    public Integer countUsersByUsername(String username) throws DaoException {
        String query = String.format(COUNT_USERS_BY_ROLE, username.toLowerCase());
        return countQuery(query);
    }

    public void removeByIdAndCourseId(Long userId, Long courseId) throws DaoException {
        executeUpdate(DELETE_USER_FROM_COURSE, userId, courseId);
    }
}
