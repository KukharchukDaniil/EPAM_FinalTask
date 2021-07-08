package com.epam.command;

import com.epam.entities.Task;
import com.epam.exceptions.ServiceException;
import com.epam.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddTaskCommand implements Command{
    public static final String TASK_NAME = "taskName";
    public static final String TASK_DESCRIPTION = "taskDescription";
    public static final String COURSE_ID = "courseId";
    public static final String COMMAND_PARAMETER_COURSE_ID = "&courseId=";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String taskName = InjectionProtector.getSafeAttribute(request,TASK_NAME);
        String taskDescription = InjectionProtector.getSafeAttribute(request, TASK_DESCRIPTION);;
        Long courseId = Long.valueOf(request.getParameter(COURSE_ID));
        Task task = new Task(null, courseId, taskName,taskDescription);
        TaskService taskService = new TaskService();
        taskService.addTask(task);
        return CommandResult.redirect(CommandTypes.SHOW_COURSE, COMMAND_PARAMETER_COURSE_ID, String.valueOf(courseId));
    }
}
