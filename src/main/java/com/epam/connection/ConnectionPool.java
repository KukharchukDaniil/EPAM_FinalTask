package com.epam.connection;

import com.epam.exceptions.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final ReentrantLock CONNECTIONS_LOCK = new ReentrantLock();
    private static final ReentrantLock REENTRANT_LOCK = new ReentrantLock();
    private static final Properties PROPERTIES = new Properties();
    private static final Logger LOGGER = LogManager.getLogger();
    private static Integer defaultPoolSize;
    private static ConnectionPool instance;
    private final Queue<ProxyConnection> availableConnections;
    private final Queue<ProxyConnection> connectionsInUse;
    private final Semaphore connectionSemaphore;
    static{
        try{
            PROPERTIES.load(new FileReader("D:\\work\\FinalProject\\src\\main\\resources\\connectionPool.properties"));
            defaultPoolSize = Integer.valueOf(PROPERTIES.getProperty("db.connections_value"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
    }

    private ConnectionPool() {
        availableConnections = new ArrayDeque<>();
        connectionsInUse = new ArrayDeque<>();
        connectionSemaphore = new Semaphore(defaultPoolSize);
    }

    public static ConnectionPool getInstance() throws DaoException {
        if (instance == null) {
            try {
                REENTRANT_LOCK.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    instance.initializeConnections(defaultPoolSize);
                }
            } catch (SQLException | ClassNotFoundException exception) {
                throw new DaoException(exception.getMessage(), exception);
            } finally {
                REENTRANT_LOCK.unlock();
            }
        }
        return instance;
    }

    private void initializeConnections(int size) throws SQLException, ClassNotFoundException {
        ConnectionFactory factory = new ConnectionFactory();
        for (int i = 0; i < size; i++) {
            ProxyConnection proxyConnection = new ProxyConnection(factory.create(), this);
            availableConnections.add(proxyConnection);
        }
    }

    public void returnConnection(ProxyConnection proxyConnection) {
        try {
            CONNECTIONS_LOCK.lock();
            if (connectionsInUse.contains(proxyConnection)) {
                availableConnections.offer(proxyConnection);
                connectionsInUse.remove(proxyConnection);
                connectionSemaphore.release();
            }
        } finally {
            CONNECTIONS_LOCK.unlock();
        }
    }

    public ProxyConnection getConnection() throws DaoException {
        try {
            CONNECTIONS_LOCK.lock();
            connectionSemaphore.acquire();
            ProxyConnection connection = availableConnections.poll();
            connectionsInUse.offer(connection);
            return connection;

        } catch (InterruptedException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            CONNECTIONS_LOCK.unlock();
        }
    }

    public void closeConnections() throws DaoException {
        try {
            for (ProxyConnection availableConnection : availableConnections) {
                availableConnection.getConnection().close();
            }
            for (ProxyConnection proxyConnection : connectionsInUse) {
                proxyConnection.getConnection().close();
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }
}
