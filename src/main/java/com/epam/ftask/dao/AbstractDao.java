package com.epam.ftask.dao;

import com.epam.ftask.entities.Entity;
import com.epam.ftask.exceptions.DaoException;
import com.epam.ftask.mapper.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.util.Optional;

public abstract class AbstractDao<T extends Entity> implements Dao<T> {
    public static final String MORE_THAN_ONE_RECORD_FOUND = "More than one record found";
    private Connection connection;
    protected final RowMapper<T> rowMapper;
    private static final String GET_BY_ID = "SELECT * FROM %s WHERE ID = ?";
    private static final String REMOVE_BY_ID = "DELETE FROM %s WHERE ID = ?";
    private static final String GET_ALL = "SELECT * FROM %s";
    private static final String COUNT_ITEMS = "SELECT COUNT(*) FROM %s";

    public AbstractDao(Connection connection, RowMapper<T> rowMapper) {
        this.connection = connection;
        this.rowMapper = rowMapper;
    }


    protected abstract String getTableName();

    protected RowMapper getRowMapper() {
        return rowMapper;
    }

    protected abstract void update(T entity) throws DaoException;

    protected abstract void create(T entity) throws DaoException;

    protected List<T> executeQuery(String query, RowMapper<T> mapper, Object... params) throws DaoException {
        try (PreparedStatement statement = createStatement(query, params);
             ResultSet resultSet = statement.executeQuery()) {
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                T entity = mapper.map(resultSet);
                entities.add(entity);
            }
            return entities;
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    public Integer countQuery(String query) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            Integer counter = resultSet.getInt(1);
            return counter;
        } catch (SQLException exception) {
            throw new DaoException(exception);
        }
    }

    private PreparedStatement createStatement(String query, Object[] params) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
        return statement;
    }

    protected Optional<T> executeForSingleResult(String query, RowMapper<T> mapper, Object... params) throws DaoException {
        List<T> items = executeQuery(query, mapper, params);
        if (items.size() == 1) {
            return Optional.of(items.get(0));
        } else if (items.size() > 1) {
            throw new IllegalArgumentException(MORE_THAN_ONE_RECORD_FOUND);
        } else {
            return Optional.empty();
        }
    }

    protected void executeUpdate(String query, Object... params) throws DaoException {
        try (PreparedStatement statement = createStatement(query, params)) {
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }
    public void removeById(Long id) throws DaoException {
        String query = String.format(REMOVE_BY_ID,getTableName());
        executeUpdate(query,id);
    }

    @Override
    public Optional<T> getById(long id) throws DaoException {
        String query = String.format(GET_BY_ID, getTableName());
        return executeForSingleResult(query, getRowMapper(), id);
    }

    @Override
    public List<T> getAllByPage(int page) throws DaoException {
        String query = String.format(GET_ALL, getTableName());
        return executeQuery(query + " LIMIT ?,5", getRowMapper(), (page - 1) * 5);
    }

    public Integer countItems() throws DaoException {
        String query = String.format(COUNT_ITEMS, getTableName());
        return countQuery(query);
    }

    @Override
    public void save(T entity) throws DaoException {
        if (entity.getId() == null) {
            create(entity);
        } else {
            update(entity);
        }
    }



}


