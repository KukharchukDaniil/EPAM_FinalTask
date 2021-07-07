package com.epam.command;

import com.epam.entities.Task;
import com.epam.exceptions.ServiceException;
import com.epam.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddTaskCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String taskName = request.getParameter("taskName");
        String taskDescription = request.getParameter("taskDescription");
        Long courseId = Long.valueOf(request.getParameter("courseId"));
        Task task = new Task(null, courseId, taskName,taskDescription);
        TaskService taskService = new TaskService();
        taskService.addTask(task);
        return CommandResult.redirect(CommandTypes.SHOW_COURSE,"&courseId=", String.valueOf(courseId));
    }
}
