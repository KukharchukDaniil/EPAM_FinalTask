package com.epam.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerMapper implements RowMapper<Integer>{
    @Override
    public Integer map(ResultSet resultSet) throws SQLException {
        return resultSet.getInt(1);
    }
}
