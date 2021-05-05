package com.epam.mapper;

import com.epam.entities.Task;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Task> {
    @Override
    public Task map(ResultSet resultSet) throws SQLException {
        return new Task(
                resultSet.getLong(1),
                resultSet.getLong(2),
                resultSet.getString(3),
                resultSet.getString(4)
        );
    }
}
