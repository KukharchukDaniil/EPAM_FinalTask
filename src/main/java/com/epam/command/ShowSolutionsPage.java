package com.epam.command;

import com.epam.dto.SolutionTaskDto;
import com.epam.dto.SolutionTaskDtoBuilder;
import com.epam.entities.Course;
import com.epam.entities.Solution;
import com.epam.entities.Task;
import com.epam.entities.User;
import com.epam.exceptions.ServiceException;
import com.epam.service.CourseService;
import com.epam.service.SolutionService;
import com.epam.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShowSolutionsPage implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        SolutionService solutionService = new SolutionService();
        CourseService courseService = new CourseService();
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        Long courseId = Long.valueOf(request.getParameter("courseId"));

        Course course = courseService.getCourseById(courseId);
        Long userId = user.getId();

        boolean isEnrolled = courseService.isUserEnrolled(userId,courseId);
        if(isEnrolled) {

            request.setAttribute("course", course);

            request.setAttribute("isEnrolled", isEnrolled);

            Integer totalItems = solutionService.countSolutionsByCourseId(courseId);
            String pageIndexString = request.getParameter("pageIndex");
            Integer pageIndex = pageIndexString != null ? Integer.parseInt(pageIndexString) : 1;
            List<Solution> allByPage = solutionService.getAllByPage(pageIndex);
            request.setAttribute("totalItems", totalItems);
            List<SolutionTaskDto> dtoList = getDtoList(allByPage);
            request.setAttribute("solutionTaskDtoList", dtoList);
            return CommandResult.forward(Destination.SOLUTIONS_PAGE.getPageAddress());
        }else{
            throw new ServiceException("Invalid access");
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
