package com.epam.ftask.command;

import com.epam.ftask.entities.Solution;
import com.epam.ftask.entities.SolutionStatus;
import com.epam.ftask.exceptions.ServiceException;
import com.epam.ftask.service.SolutionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendSolutionCommand implements Command {
    private static final String TASK_ID = "taskId";
    private static final String USER_ID = "userId";
    private static final String COURSE_ID = "courseId";
    private static final String SOLUTION_VALUE = "solutionValue";
    private static final String COURSE_ID_PARAMETER = "&courseId=";
    private SolutionService service;
    public SendSolutionCommand(SolutionService solutionService) {
        this.service = solutionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Long taskId = Long.parseLong(request.getParameter(TASK_ID));
        Long userId = Long.parseLong(request.getParameter(USER_ID));
        String courseId = request.getParameter(COURSE_ID);
        String solutionValue = InjectionProtector.getSafeAttribute(request, SOLUTION_VALUE);
        Solution solution = new Solution(null,taskId,userId, SolutionStatus.SENT,solutionValue,0,"");
        service.commitSolution(solution);
        return CommandResult.redirect(CommandTypes.SHOW_COURSE, COURSE_ID_PARAMETER,courseId);
    }
}
