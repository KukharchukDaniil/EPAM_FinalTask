package com.epam.dao;

import com.epam.entities.Entity;
import com.epam.exceptions.DaoException;

import java.util.Optional;
import java.util.List;


public interface Dao<T extends Entity>{
    Optional<T> getById(long id) throws DaoException;
    List<T> getAllByPage(int page) throws DaoException;
    void save(T entity) throws DaoException;
    void removeById(long id) throws DaoException;
}
