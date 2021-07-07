package com.epam.dao;

import com.epam.connection.ConnectionPool;
import com.epam.exceptions.DaoException;

public class DaoHelperFactory {
    public final DaoHelper create() throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        return new DaoHelper(connectionPool.getConnection());
    }
}

