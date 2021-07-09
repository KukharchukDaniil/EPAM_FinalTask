package com.epam.ftask.mapper;

import com.epam.ftask.entities.Solution;
import com.epam.ftask.entities.SolutionStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class SolutionRowMapper implements RowMapper<Solution>{
    @Override
    public Solution map(ResultSet resultSet) throws SQLException {
        return new Solution(
                resultSet.getLong(1),
                resultSet.getLong(2),
                resultSet.getLong(3),
                SolutionStatus.valueOf( resultSet.getString(4).toUpperCase(Locale.ROOT)),
                resultSet.getString(5), resultSet.getInt(6),
                resultSet.getString(7));
    }
}
