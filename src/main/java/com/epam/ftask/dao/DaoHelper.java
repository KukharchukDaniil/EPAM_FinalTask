package com.epam.ftask.dao;

import com.epam.ftask.connection.ProxyConnection;
import com.epam.ftask.exceptions.DaoException;
import com.epam.ftask.mapper.CourseRowMapper;
import com.epam.ftask.mapper.SolutionRowMapper;
import com.epam.ftask.mapper.TaskRowMapper;
import com.epam.ftask.mapper.UserRowMapper;

import java.sql.SQLException;

public class DaoHelper implements AutoCloseable{
    private ProxyConnection connection;

    public DaoHelper(ProxyConnection connection) {
        this.connection = connection;
    }

    public AbstractDao create(DaoType daoType) throws DaoException {
        switch (daoType){
            case COURSE_DAO:
                return new CourseDao(connection, new CourseRowMapper());
            case USER_DAO:
                return new UserDao(connection , new UserRowMapper());
            case TASK_DAO:
                return new TaskDao(connection, new TaskRowMapper());
            case SOLUTION_DAO:
                return new SolutionDao(connection, new SolutionRowMapper());
            default:
                throw new DaoException("Unsupported DaoType");
        }
    }

    @Override
    public void close() throws DaoException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        }
    }
    public void startTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public void endTransaction() throws DaoException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
