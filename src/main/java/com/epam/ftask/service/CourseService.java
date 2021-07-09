package com.epam.ftask.service;

import com.epam.ftask.dao.CourseDao;
import com.epam.ftask.dao.DaoHelper;
import com.epam.ftask.dao.DaoType;
import com.epam.ftask.entities.Course;
import com.epam.ftask.exceptions.ServiceException;

import java.util.List;

public class CourseService extends AbstractService<Course> {


    @Override
    protected DaoType getDaoType() {
        return DaoType.COURSE_DAO;
    }

    public List<Course> getMyCoursesByPage(long userId, int pageIndex) throws ServiceException {
        try (DaoHelper daoHelper = getDaoHelper()) {
            CourseDao dao = (CourseDao) daoHelper.create(getDaoType());
            return dao.getCoursesByUserIdAndPage(userId, pageIndex);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Boolean isUserEnrolled(long userId, long courseId) throws ServiceException {
        try (DaoHelper daoHelper = getDaoHelper()) {
            CourseDao dao = (CourseDao) daoHelper.create(getDaoType());
            return dao.isUserEnrolled(userId, courseId);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Integer countMyCourses(long userId) throws ServiceException {
        try (DaoHelper daoHelper = getDaoHelper()) {
            CourseDao dao = (CourseDao) daoHelper.create(getDaoType());
            Integer counter = dao.countCoursesByUserId(userId);
            return counter;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Course getCourseById(long courseId) throws ServiceException {
        try (DaoHelper daoHelper = getDaoHelper()) {
            CourseDao dao = (CourseDao) daoHelper.create(getDaoType());
            return dao.getCourseById(courseId);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Integer countAllCourses() throws ServiceException {
        try (DaoHelper daoHelper = getDaoHelper()) {
            CourseDao dao = (CourseDao) daoHelper.create(getDaoType());
            Integer counter = dao.countItems();
            return counter;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }public void saveCourse(Course course) throws ServiceException {
        try (DaoHelper daoHelper = getDaoHelper()) {
            daoHelper.startTransaction();
            CourseDao dao = (CourseDao) daoHelper.create(getDaoType());
            dao.save(course);
            daoHelper.endTransaction();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }


}
