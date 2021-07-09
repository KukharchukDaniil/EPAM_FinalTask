package com.epam.ftask.command;

import com.epam.ftask.entities.Task;
import com.epam.ftask.exceptions.ServiceException;
import com.epam.ftask.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddTaskCommand implements Command {
    private static final String TASK_NAME = "taskName";
    private static final String TASK_DESCRIPTION = "taskDescription";
    private static final String COURSE_ID = "courseId";
    private static final String COMMAND_PARAMETER_COURSE_ID = "&courseId=";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        String taskName = InjectionProtector.getSafeAttribute(request, TASK_NAME);
        String taskDescription = InjectionProtector.getSafeAttribute(request, TASK_DESCRIPTION);
        ;
        Long courseId = Long.valueOf(request.getParameter(COURSE_ID));
        Task task = new Task(null, courseId, taskName, taskDescription);
        TaskService taskService = new TaskService();
        taskService.addTask(task);
        return CommandResult.redirect(CommandTypes.SHOW_COURSE, COMMAND_PARAMETER_COURSE_ID, String.valueOf(courseId));
    }
}

