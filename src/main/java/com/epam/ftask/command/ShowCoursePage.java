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

public class ShowCoursePage implements Command {
    private static final String USER = "user";
    private static final String COURSE_ID = "courseId";
    private static final String IS_ENROLLED = "isEnrolled";
    private static final String COURSE = "course";
    private static final String PAGE_INDEX = "pageIndex";
    private static final String TOTAL_ITEMS = "totalItems";
    private static final String SOLUTION_TASK_DTO_LIST = "solutionTaskDtoList";
    private final CourseService courseService;
    private final TaskService taskService;
    private final SolutionService solutionService;

    public ShowCoursePage(CourseService courseService, TaskService taskService, SolutionService solutionService) {
        this.courseService = courseService;
        this.taskService = taskService;
        this.solutionService = solutionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        Long courseId = Long.valueOf(request.getParameter(COURSE_ID));
        Long userId = user.getId();

        Boolean isEnrolled = courseService.isUserEnrolled(userId, courseId);
        request.setAttribute(IS_ENROLLED, isEnrolled);

        Course course = courseService.getCourseById(courseId);
        request.setAttribute(COURSE, course);

        Integer totalItems = taskService.countTasksByCourseId(courseId);

        String pageIndexString = request.getParameter(PAGE_INDEX);
        Integer pageIndex = pageIndexString != null ? Integer.parseInt(pageIndexString) : 1;
        List<Task> tasksByCourseId = taskService.getTasksByCourseIdAndPage(courseId, pageIndex);
        request.setAttribute(PAGE_INDEX, pageIndex);
        request.setAttribute(TOTAL_ITEMS, totalItems);

        List<SolutionTaskDto> dtoList = mapDtoList(tasksByCourseId,userId);

        request.setAttribute(SOLUTION_TASK_DTO_LIST, dtoList);
        return CommandResult.forward(Destination.COURSE_PAGE);
    }

    private List<SolutionTaskDto> mapDtoList(List<Task> tasksByCourseId, Long userId) throws ServiceException {
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
        return dtoList;
    }
}
