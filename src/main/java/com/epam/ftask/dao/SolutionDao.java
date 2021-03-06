package com.epam.ftask.dao;

import com.epam.ftask.entities.Solution;
import com.epam.ftask.exceptions.DaoException;
import com.epam.ftask.mapper.RowMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class SolutionDao extends AbstractDao<Solution> {
    private static final String TABLE_NAME = "solutions";
    private static final String UPDATE_SOLUTION = "UPDATE SOLUTIONS " +
            "SET SOLUTION_STATUS = ?,VALUE = ?, MARK = ?, COMMENT = ? WHERE id = ?";
    private static final String GET_BY_TASK_AND_USER = "SELECT * FROM SOLUTIONS WHERE TASK_ID = ? AND USER_ID = ?";
    private static final String INSERT_SOLUTION =
            "INSERT INTO SOLUTIONS(TASK_ID,USER_ID,SOLUTION_STATUS,VALUE,MARK,COMMENT) VALUES(?, ?, ?, ?,?,?)";
    private static final String COUNT_TASKS_BY_COURSE_ID = "SELECT COUNT(*) FROM courses_db.solutions INNER JOIN tasks ON task_id = tasks.id where tasks.course_id = %s";;
    private static final String GET_BY_COURSE_AND_PAGE = "SELECT * FROM courses_db.solutions JOIN courses_db.tasks " +
            "ON (task_id = tasks.id and tasks.course_id=?) LIMIT ?,5";;
    public static final String INVALID_SOLUTION_ID = "Invalid solution id! Can't update solution.";

    public SolutionDao(Connection connection, RowMapper<Solution> rowMapper) {
        super(connection, rowMapper);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    public Optional<Solution> getSolutionByUserIdAndTaskId(Long taskId, Long userId) throws DaoException {
        return executeForSingleResult(GET_BY_TASK_AND_USER, getRowMapper(), taskId, userId);
    }

    @Override
    protected void update(Solution entity) throws DaoException {
        Optional<Solution> solution = getById(entity.getId());
        if (!solution.isPresent()) {
            throw new DaoException(INVALID_SOLUTION_ID);
        }
        executeUpdate(
                UPDATE_SOLUTION,
                entity.getStatus().toString().toLowerCase() ,
                entity.getValue(),
                entity.getMark(),
                entity.getComment(),
                entity.getId()
        );
    }

    @Override
    protected void create(Solution entity) throws DaoException {
        executeUpdate(
                INSERT_SOLUTION,
                entity.getTaskId(),
                entity.getUserId(),
                entity.getStatus().toString(),
                entity.getValue(),
                entity.getMark(),
                entity.getComment()

        );
    }

    public Integer countSolutionsByCourseId(long courseId) throws DaoException {
        String query = String.format(COUNT_TASKS_BY_COURSE_ID, courseId);
        return countQuery(query);
    }

    public List<Solution> getSolutionsByCourseIdAndPage(Long courseId, Integer pageIndex) throws DaoException {
       return executeQuery(GET_BY_COURSE_AND_PAGE,rowMapper,courseId,(1-pageIndex)*5);
    }
}
