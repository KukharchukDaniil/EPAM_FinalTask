package com.epam.ftask.dao;

import com.epam.ftask.connection.ConnectionPool;
import com.epam.ftask.exceptions.DaoException;

public class DaoHelperFactory {
    public final DaoHelper create() throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        return new DaoHelper(connectionPool.getConnection());
    }
}

