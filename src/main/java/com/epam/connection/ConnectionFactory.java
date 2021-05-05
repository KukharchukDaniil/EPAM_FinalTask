package com.epam.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class ConnectionFactory {
    private static final String DB_LOGIN = "admin";
    private static final String DB_PASSWORD = "admin";
    private static final String DB_URL = "jdbc:mysql://localhost/courses_db";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public ConnectionFactory() throws ClassNotFoundException {
    }

    Connection create() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        Connection connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);
        return connection;
    }

}
