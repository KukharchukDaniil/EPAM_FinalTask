package com.epam.mapper;

import com.epam.entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper <T> {
    T map(ResultSet resultSet) throws SQLException;
}
