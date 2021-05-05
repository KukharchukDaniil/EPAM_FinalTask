package com.epam.service;

import com.epam.dao.DaoHelper;
import com.epam.dao.DaoType;
import com.epam.dao.TaskDao;
import com.epam.entities.Task;
import com.epam.exceptions.DaoException;

import java.util.List;

public class TaskService extends AbstractService<Task>{
    @Override
    protected DaoType getDaoType() {
        return DaoType.TASK_DAO;
    }
    public List<Task> getTasksByCourseId(Long courseId) throws DaoException {
        try(DaoHelper helper = getDaoHelper()){
            TaskDao dao = (TaskDao) helper.create(getDaoType());
            return dao.getTasksByCourseId(courseId);
        } catch (Exception e) {
            throw new DaoException(e.getMessage(),e);
        }
    }
}
