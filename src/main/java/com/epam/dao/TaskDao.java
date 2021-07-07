package com.epam.dao;

import com.epam.entities.Task;
import com.epam.exceptions.DaoException;
import com.epam.mapper.RowMapper;
import com.epam.mapper.TaskRowMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class TaskDao extends AbstractDao<Task> {
    private static final String TABLE_NAME = "tasks";
    private static final String UPDATE_TASK_COURSE_NAME_AND_DESCRIPTION = "UPDATE TASKS SET COURSE_ID = ?, " +
            "NAME = ?, DESCRIPTION = ?, WHERE ID = ? ";
    private static final String INSERT_TASK = "INSERT INTO TASKS(COURSE_ID,NAME,DESCRIPTION) VALUES(?, ?, ?)";
    private static final String FIND_BY_COURSE_ID = "SELECT * FROM TASKS WHERE COURSE_ID = ?";
    private static final String DELETE_BY_ID = "DELETE FROM TASKS WHERE ID = ?";
    private static final String FIND_BY_USER_ID_AND_PAGE = "SELECT * FROM courses_db.tasks WHERE course_id = ? LIMIT ?,5";
    private static final String COUNT_TASKS_BY_COURSE_ID = "SELECT COUNT(*) FROM tasks WHERE course_id = %s";

    public TaskDao(Connection connection, RowMapper<Task> rowMapper) {
        super(connection, rowMapper);
    }
    public TaskDao(Connection connection) {
        super(connection, new TaskRowMapper());
    }
    public List<Task> getTasksByCourseId(final Long courseId) throws DaoException {
        return executeQuery(FIND_BY_COURSE_ID, rowMapper, courseId);
    }
    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
    public List<Task> getCoursesByUserIdAndPage(long userId, int pageIndex) throws DaoException {
        return executeQuery(FIND_BY_USER_ID_AND_PAGE, rowMapper, userId, (pageIndex - 1) * 5);
    }
    public Integer countTasksByCourseId(long courseId) throws DaoException {
        String query = String.format(COUNT_TASKS_BY_COURSE_ID, courseId);
        return countQuery(query);
    }
    public void deleteById(long taskId) throws DaoException {
        executeUpdate(DELETE_BY_ID,taskId);
    }

    @Override
    protected void update(Task entity) throws DaoException {
        Optional<Task> task = getById(entity.getId());
        if (!task.isPresent()) {
            throw new DaoException("Invalid task id! Can't update task.");
        }
        executeUpdate(
                UPDATE_TASK_COURSE_NAME_AND_DESCRIPTION,
                entity.getCourseId(),
                entity.getName(),
                entity.getDescription(),
                entity.getId()
        );
    }

    @Override
    protected void create(Task entity) throws DaoException {
        executeUpdate(INSERT_TASK, entity.getCourseId(), entity.getName(), entity.getDescription());
    }
}
