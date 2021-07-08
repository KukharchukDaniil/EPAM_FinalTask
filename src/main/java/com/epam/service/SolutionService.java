package com.epam.service;

import com.epam.dao.DaoHelper;
import com.epam.dao.DaoType;
import com.epam.dao.SolutionDao;
import com.epam.entities.Solution;
import com.epam.exceptions.ServiceException;

import java.util.Optional;

public class SolutionService extends AbstractService<Solution> {

    public Optional<Solution> getSolutionByUserIdAndTaskId(Long taskId, Long userId) throws ServiceException {
        try (DaoHelper helper = getDaoHelper()) {
            SolutionDao dao = (SolutionDao) helper.create(getDaoType());
            return dao.getSolutionByUserIdAndTaskId(taskId, userId);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void commitSolution(Solution solution) throws ServiceException {
        try (DaoHelper helper = getDaoHelper()) {
            helper.startTransaction();

            SolutionDao dao = (SolutionDao) helper.create(getDaoType());
            Solution bufferSolution = solution;

            Optional<Solution> solutionFromDatabase = dao.getSolutionByUserIdAndTaskId(solution.getTaskId(), solution.getUserId());
            if(solutionFromDatabase.isPresent()){
                bufferSolution.setId(solutionFromDatabase.get().getId());
            }
            dao.save(bufferSolution);
            helper.endTransaction();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    protected DaoType getDaoType() {
        return DaoType.SOLUTION_DAO;
    }

    public Integer countSolutionsByCourseId(long courseId) throws ServiceException {
        try (DaoHelper helper = getDaoHelper()) {

            SolutionDao dao = (SolutionDao) helper.create(getDaoType());
            return dao.countSolutionsByCourseId(courseId);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
