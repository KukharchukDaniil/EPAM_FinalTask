package com.epam.ftask.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides an interface for mapping T elements from ResultSet
 * @param <T>
 */
public interface RowMapper <T> {
    T map(ResultSet resultSet) throws SQLException;
}
