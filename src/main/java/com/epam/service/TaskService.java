package com.epam.service;

import com.epam.dao.DaoHelper;
import com.epam.dao.DaoType;
import com.epam.dao.TaskDao;
import com.epam.entities.Task;
import com.epam.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public class TaskService extends AbstractService<Task> {
    @Override
    protected DaoType getDaoType() {
        return DaoType.TASK_DAO;
    }

    public List<Task> getTasksByCourseIdAndPage(Long courseId, int pageIndex) throws ServiceException {
        try (DaoHelper helper = getDaoHelper()) {
            TaskDao dao = (TaskDao) helper.create(getDaoType());
            return dao.getCoursesByUserIdAndPage(courseId,pageIndex);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void deleteTaskById(Long taskId) throws ServiceException {
        try (DaoHelper helper = getDaoHelper()) {
            helper.startTransaction();
            TaskDao dao = (TaskDao) helper.create(getDaoType());
            dao.removeById(taskId);
            helper.endTransaction();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Optional<Task> getTaskById(Long taskId) throws ServiceException {
        try (DaoHelper helper = getDaoHelper()) {
            TaskDao dao = (TaskDao) helper.create(getDaoType());
            return dao.getById(taskId);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    public Integer countTasksByCourseId(long courseId) throws ServiceException {
        try (DaoHelper helper = getDaoHelper()) {
            TaskDao dao = (TaskDao) helper.create(getDaoType());
            return dao.countTasksByCourseId(courseId);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    public void addTask(Task task) throws ServiceException {
        try (DaoHelper helper = getDaoHelper()) {

            TaskDao dao = (TaskDao) helper.create(getDaoType());
            dao.save(task);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
