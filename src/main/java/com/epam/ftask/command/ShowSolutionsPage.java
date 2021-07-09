package com.epam.ftask.command;

import com.epam.ftask.dto.SolutionTaskDto;
import com.epam.ftask.dto.SolutionTaskDtoBuilder;
import com.epam.ftask.entities.Course;
import com.epam.ftask.entities.Solution;
import com.epam.ftask.entities.Task;
import com.epam.ftask.entities.User;
import com.epam.ftask.exceptions.ServiceException;
import com.epam.ftask.service.CourseService;
import com.epam.ftask.service.SolutionService;
import com.epam.ftask.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShowSolutionsPage implements Command {

    private static final String USER = "user";
    private static final String COURSE_ID = "courseId";
    private static final String COURSE = "course";
    private static final String IS_ENROLLED = "isEnrolled";
    private static final String PAGE_INDEX = "pageIndex";
    private static final String TOTAL_ITEMS = "totalItems";
    private static final String SOLUTION_TASK_DTO_LIST = "solutionTaskDtoList";
    private static final String INVALID_ACCESS = "Invalid access";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        SolutionService solutionService = new SolutionService();
        CourseService courseService = new CourseService();
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute(USER);
        Long courseId = Long.valueOf(request.getParameter(COURSE_ID));

        Course course = courseService.getCourseById(courseId);
        Long userId = user.getId();

        boolean isEnrolled = courseService.isUserEnrolled(userId, courseId);
        if(isEnrolled) {

            request.setAttribute(COURSE, course);

            request.setAttribute(IS_ENROLLED, isEnrolled);

            Integer totalItems = solutionService.countSolutionsByCourseId(courseId);
            String pageIndexString = request.getParameter(PAGE_INDEX);
            Integer pageIndex = pageIndexString != null ? Integer.parseInt(pageIndexString) : 1;
            List<Solution> allByPage = solutionService.getSolutionsByCourseIdAndPage(courseId,pageIndex);
            request.setAttribute(TOTAL_ITEMS, totalItems);
            List<SolutionTaskDto> dtoList = getDtoList(allByPage);
            request.setAttribute(SOLUTION_TASK_DTO_LIST, dtoList);
            return CommandResult.forward(Destination.SOLUTIONS_PAGE);
        }else{
            throw new ServiceException(INVALID_ACCESS);
        }
    }

    private List<SolutionTaskDto> getDtoList(List<Solution> allByPage) throws ServiceException {

        TaskService taskService = new TaskService();
        List<SolutionTaskDto> dtoList = new ArrayList<>();
        for (Solution solution : allByPage) {
            Optional<Task> taskById = taskService.getTaskById(solution.getTaskId());
            SolutionTaskDto dto;
            SolutionTaskDtoBuilder builder = new SolutionTaskDtoBuilder();
            builder.setSolutionId(solution.getId())
                    .setMark(solution.getMark())
                    .setComment(solution.getComment())
                    .setStatus(solution.getStatus())
                    .setValue(solution.getValue())
                    .setUserId(solution.getUserId());
            if (taskById.isPresent()) {
                Task task = taskById.get();
                builder.setTaskId(task.getId())
                        .setTaskName(task.getName())
                        .setTaskDescription(task.getDescription());
            }
            dto = builder.build();
            dtoList.add(dto);
        }
        return dtoList;
    }
}
