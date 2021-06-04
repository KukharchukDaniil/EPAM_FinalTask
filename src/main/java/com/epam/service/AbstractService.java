package com.epam.service;

import com.epam.dao.*;
import com.epam.entities.Course;
import com.epam.entities.Entity;
import com.epam.exceptions.DaoException;
import com.epam.exceptions.ServiceException;

import java.util.List;

public abstract class AbstractService<T extends Entity>{
    private final DaoHelperFactory daoHelperFactory;

    public AbstractService() {
        daoHelperFactory = new DaoHelperFactory();
    }

    public AbstractService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }
    protected DaoHelper getDaoHelper() throws DaoException {
        return daoHelperFactory.create();
    }
    public List<T> getAllByPage(int page) throws ServiceException {
        try(DaoHelper daoHelper = getDaoHelper()) {
            AbstractDao<T> dao = daoHelper.create(getDaoType());
            return dao.getAllByPage(page);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    protected abstract DaoType getDaoType();
}
