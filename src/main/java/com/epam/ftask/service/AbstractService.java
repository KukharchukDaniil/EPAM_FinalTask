package com.epam.ftask.service;

import com.epam.ftask.entities.Entity;
import com.epam.ftask.exceptions.DaoException;
import com.epam.ftask.exceptions.ServiceException;
import com.epam.ftask.dao.AbstractDao;
import com.epam.ftask.dao.DaoHelper;
import com.epam.ftask.dao.DaoHelperFactory;
import com.epam.ftask.dao.DaoType;

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
    public void deleteById(Long id) throws ServiceException {
        try(DaoHelper daoHelper = getDaoHelper()) {
            daoHelper.startTransaction();
            AbstractDao<T> dao = daoHelper.create(getDaoType());
            dao.removeById(id);
            daoHelper.endTransaction();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    protected abstract DaoType getDaoType();
}
