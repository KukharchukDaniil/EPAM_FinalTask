package com.epam.ftask.mapper;

import com.epam.ftask.entities.User;
import com.epam.ftask.entities.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User map(ResultSet resultSet) throws SQLException {
        User user = new User(
                resultSet.getLong(User.ID),
                resultSet.getString(User.NAME),
                resultSet.getString(User.USERNAME),
                resultSet.getString(User.PASSWORD),
                UserRole.valueOf( resultSet.getString(User.ROLE).toUpperCase(Locale.ROOT)));
        return user;
    }
}
