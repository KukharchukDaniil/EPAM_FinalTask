package com.epam.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class ConnectionFactory {
    private static String DB_URL = "jdbc:mysql://localhost/courses_db";
    private static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final Properties PROPERTIES = new Properties();
    private static final Logger LOGGER = LogManager.getLogger();

    static {
        try {
            PROPERTIES.load(new FileReader("D:\\work\\FinalProject\\src\\main\\resources\\database.properties"));

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    //TODO: resourseBundle
    public ConnectionFactory() throws ClassNotFoundException {
        Class.forName(DB_DRIVER);
    }

    /* package-private */ Connection create() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, PROPERTIES);
        return connection;
    }

}
