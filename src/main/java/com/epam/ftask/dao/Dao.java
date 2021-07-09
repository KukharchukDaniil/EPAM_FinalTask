package com.epam.ftask.dao;

import com.epam.ftask.entities.Entity;
import com.epam.ftask.exceptions.DaoException;

import java.util.Optional;
import java.util.List;


public interface Dao<T extends Entity> {

    /**
     * Tries to find T element in database by element's id.
     *
     * @param id
     * @return Optional.of(T t)
     * @throws DaoException if more than one element was found
     */
    Optional<T> getById(long id) throws DaoException;


    /**
     * @param page - page index
     * @return List<T> according to the page index
     * @throws DaoException
     */
    List<T> getAllByPage(int page) throws DaoException;

    /**
     * Saves the entity in the database
     * @param entity
     * @throws DaoException
     */
    void save(T entity) throws DaoException;
}
