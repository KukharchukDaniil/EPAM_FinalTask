package com.epam.service;

import com.epam.dao.DaoHelper;
import com.epam.dao.DaoType;
import com.epam.dao.SolutionDao;
import com.epam.entities.Solution;
import com.epam.exceptions.DaoException;
import com.epam.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public class SolutionService extends AbstractService<Solution>{

    public Optional<Solution> getSolutionByUserIdAndTaskId(Long taskId, Long userId) throws DaoException {
        try(DaoHelper helper = getDaoHelper()){
            SolutionDao dao = (SolutionDao) helper.create(getDaoType());
            return dao.getSolutionByUserIdAndTaskId(taskId,userId);
        } catch (Exception e) {
            throw new DaoException(e.getMessage(),e);
        }
    }
    public void commitSolution(Solution solution) throws ServiceException {
        try (DaoHelper helper = getDaoHelper()){
            SolutionDao dao = (SolutionDao)helper.create(getDaoType());
            dao.save(solution);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }
    @Override
    protected DaoType getDaoType() {
        return DaoType.SOLUTION_DAO;
    }
}
