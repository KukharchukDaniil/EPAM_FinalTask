package com.epam.service;

import com.epam.dao.DaoHelper;
import com.epam.dao.DaoType;
import com.epam.dao.UserDao;
import com.epam.entities.User;
import com.epam.exceptions.DaoException;
import com.epam.exceptions.ServiceException;

import java.util.Optional;

public class UserService extends AbstractService<User>{

    public Optional<User> login(String login, String password) throws ServiceException {
        try(DaoHelper daoHelper = getDaoHelper()) {
            UserDao dao = (UserDao) daoHelper.create(getDaoType());
            return dao.findUserByLoginAndPassword(login, password);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    public void enrollOnCourse(long courseId, long userId) throws ServiceException {
        try(DaoHelper daoHelper = getDaoHelper()) {
            UserDao dao = (UserDao) daoHelper.create(getDaoType());
            dao.enrollOnCourse(courseId, userId);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    public boolean doesUserExist(String username) throws ServiceException {
        try(DaoHelper daoHelper = getDaoHelper()){
            UserDao dao = (UserDao) daoHelper.create(getDaoType());
            return dao.findUserByUsername(username).isPresent();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    public void signUpUser(User entity) throws ServiceException {
        try(DaoHelper daoHelper = getDaoHelper()){
            UserDao dao = (UserDao) daoHelper.create(getDaoType());
            dao.create(entity);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    @Override
    protected DaoType getDaoType() {
        return DaoType.USER_DAO;
    }
}
