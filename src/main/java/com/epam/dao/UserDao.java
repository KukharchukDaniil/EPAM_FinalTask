package com.epam.dao;

import com.epam.entities.User;
import com.epam.exceptions.DaoException;
import com.epam.mapper.RowMapper;
import com.epam.mapper.UserRowMapper;

import java.sql.*;
import java.util.Optional;

public class UserDao extends AbstractDao<User> {
    private static final String FIND_BY_LOGIN_AND_PASSWORD = "SELECT ID, NAME, USERNAME, PASSWORD, ROLE FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
    private static final String INSERT_USER = "INSERT INTO USERS(NAME,USERNAME,PASSWORD) VALUES(?, ?, ?)";
    private static final String ENROLL_USER = "INSERT INTO USERS_COURSES(`course_id`, `user_id`) VALUES (?, ?);";
    private static final String UPDATE_USER_NAME_AND_PASSWORD = "UPDATE USERS SET NAME = ?, PASSWORD = ?, WHERE ID = ? ";
    private static final String TABLE_NAME = "USERS";
    private static final String FIND_USER_BY_USERNAME = "SELECT * FROM USERS WHERE USERNAME = ?";

    public UserDao(Connection connection, UserRowMapper userRowMapper) {
        super(connection, userRowMapper);
    }

    public Optional<User> findUserByLoginAndPassword(final String login, final String password) throws DaoException {
        return executeForSingleResult(FIND_BY_LOGIN_AND_PASSWORD, rowMapper, login, password);
    }
    public void enrollOnCourse(long courseId, long userId) throws DaoException {
        executeUpdate(ENROLL_USER,courseId,userId);
    }
    public Optional<User> findUserByUsername(String username) throws DaoException {
        return executeForSingleResult(FIND_USER_BY_USERNAME,rowMapper, username);
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
        System.out.println(entity.getName() + entity.getPassword() + entity.getUsername());
        executeUpdate(INSERT_USER,entity.getName(),entity.getUsername(),entity.getPassword());
    }
}
