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
import com.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShowCoursePage implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        CourseService courseService = new CourseService();
        TaskService taskService = new TaskService();
        SolutionService solutionService = new SolutionService();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Long courseId = Long.valueOf(request.getParameter("courseId"));
        Long userId = user.getId();

        Boolean isEnrolled = courseService.isUserEnrolled(userId, courseId);
        request.setAttribute("isEnrolled",isEnrolled);

        Course course = courseService.getCourseById(courseId);
        request.setAttribute("course", course);

        Integer totalItems = taskService.countTasksByCourseId(courseId);

        String pageIndexString = request.getParameter("pageIndex");
        Integer pageIndex = pageIndexString!=null? Integer.valueOf(pageIndexString):1;
        List<Task> tasksByCourseId = taskService.getTasksByCourseIdAndPage(courseId,pageIndex);
        request.setAttribute("pageIndex",pageIndex);
        request.setAttribute("totalItems",totalItems);
        List<SolutionTaskDto> dtoList = new ArrayList<>();
        for (Task task : tasksByCourseId) {
            Long taskId = task.getId();
            Optional<Solution> solutionOptional = solutionService.getSolutionByUserIdAndTaskId(taskId, userId);
            SolutionTaskDtoBuilder dtoBuilder = new SolutionTaskDtoBuilder()
                    .setTaskId(taskId)
                    .setTaskDescription(task.getDescription())
                    .setTaskName(task.getName())
                    .setUserId(userId);
            if (solutionOptional.isPresent()) {
                Solution solution = solutionOptional.get();
                dtoBuilder
                        .setSolutionId(solution.getId())
                        .setMark(solution.getMark())
                        .setComment(solution.getComment())
                        .setStatus(solution.getStatus())
                        .setValue(solution.getValue());
            }
            SolutionTaskDto dto = dtoBuilder.build();
            dtoList.add(dto);
        }
        request.setAttribute("solutionTaskDtoList", dtoList);
        return CommandResult.forward(Pages.COURSE_PAGE);
    }
}
