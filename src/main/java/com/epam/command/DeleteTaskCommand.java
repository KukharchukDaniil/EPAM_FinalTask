package com.epam.command;

import com.epam.entities.Task;
import com.epam.exceptions.ServiceException;
import com.epam.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class DeleteTaskCommand implements Command{
    private static final String TASK_ID = "taskId";
    private static final String COURSE_ID = "courseId";
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        TaskService taskService = new TaskService();
        Long taskId = Long.valueOf(request.getParameter(TASK_ID));
        Long courseId = Long.valueOf(request.getParameter(COURSE_ID));
        Optional<Task> taskOptional = taskService.getTaskById(taskId);
        if(taskOptional.isPresent()){
            taskService.deleteTaskById(taskId);
        }
        return CommandResult.redirect(CommandFactory.SHOW_COURSE,"&courseId=", String.valueOf(courseId));
    }
}
