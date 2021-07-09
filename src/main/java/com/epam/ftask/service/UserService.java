package com.epam.ftask.service;

import com.epam.ftask.dao.DaoHelper;
import com.epam.ftask.dao.DaoType;
import com.epam.ftask.dao.UserDao;
import com.epam.ftask.entities.User;
import com.epam.ftask.entities.UserRole;
import com.epam.ftask.exceptions.ServiceException;

import java.util.List;
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
            daoHelper.startTransaction();
            UserDao dao = (UserDao) daoHelper.create(getDaoType());
            dao.enrollOnCourse(courseId, userId);
            daoHelper.endTransaction();
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
            daoHelper.startTransaction();
            UserDao dao = (UserDao) daoHelper.create(getDaoType());
            dao.create(entity);
            daoHelper.endTransaction();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    public List<User> findUsersByRoleAndPage(UserRole role, Integer pageIndex) throws ServiceException {
        try (DaoHelper daoHelper = getDaoHelper()) {
            UserDao dao = (UserDao) daoHelper.create(getDaoType());
            return dao.findUsersByRoleAndPage(role, pageIndex);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    @Override
    protected DaoType getDaoType() {
        return DaoType.USER_DAO;
    }

    public Integer countUsersByRole(UserRole role) throws ServiceException {
        try (DaoHelper daoHelper = getDaoHelper()) {
            UserDao dao = (UserDao) daoHelper.create(getDaoType());
            Integer counter = dao.countUsersByRole(role);
            return counter;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }public void removeUserFromCourse(Long userId, Long courseId) throws ServiceException {
        try (DaoHelper daoHelper = getDaoHelper()) {
            UserDao dao = (UserDao) daoHelper.create(getDaoType());
            dao.removeByIdAndCourseId(userId,courseId);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Boolean isUsernameUnique(String name) throws ServiceException {
        try (DaoHelper daoHelper = getDaoHelper()) {
            UserDao dao = (UserDao) daoHelper.create(getDaoType());
            Integer counter = dao.countUsersByUsername(name);
            return counter == 0;

        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }


}
