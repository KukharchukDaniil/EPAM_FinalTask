package com.epam.dao;

import com.epam.entities.Solution;
import com.epam.exceptions.DaoException;
import com.epam.mapper.RowMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class SolutionDao extends AbstractDao<Solution> {
    private static final String TABLE_NAME = "solutions";
    private static final String UPDATE_SOLUTION = "UPDATE SOLUTIONS " +
            "SET SOLUTION_STATUS = ?,VALUE = ?, MARK = ?, COMMENT = ?";
    private static final String GET_BY_TASK_AND_USER = "SELECT * FROM SOLUTIONS WHERE TASK_ID = ? AND USER_ID = ?";
    private static final String INSERT_SOLUTION =
            "INSERT INTO SOLUTIONS(TASK_ID,USER_ID,SOLUTION_STATUS,VALUE,MARK,COMMENT) VALUES(?, ?, ?, ?,?,?)";

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
            throw new DaoException("Invalid solution id! Can't update solution.");
        }
        executeUpdate(
                UPDATE_SOLUTION,
                entity.getStatus(),
                entity.getValue(),
                entity.getMark()
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
}