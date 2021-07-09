package com.epam.ftask.command;

import com.epam.ftask.entities.Task;
import com.epam.ftask.exceptions.ServiceException;
import com.epam.ftask.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class DeleteTaskCommand implements Command{
    private static final String TASK_ID = "taskId";
    private static final String COURSE_ID = "courseId";
    public static final String COMMAND_PARAMETE_COURSE_ID = "&courseId=";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        TaskService taskService = new TaskService();
        Long taskId = Long.valueOf(request.getParameter(TASK_ID));
        Long courseId = Long.valueOf(request.getParameter(COURSE_ID));
        Optional<Task> taskOptional = taskService.getTaskById(taskId);
        if(taskOptional.isPresent()){
            taskService.deleteTaskById(taskId);
        }
        return CommandResult.redirect(CommandTypes.SHOW_COURSE, COMMAND_PARAMETE_COURSE_ID, String.valueOf(courseId));
    }
}
