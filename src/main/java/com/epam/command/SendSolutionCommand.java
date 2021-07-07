package com.epam.command;

import com.epam.entities.Solution;
import com.epam.entities.SolutionStatus;
import com.epam.exceptions.ServiceException;
import com.epam.service.SolutionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendSolutionCommand implements Command {
    private SolutionService service;
    public SendSolutionCommand(SolutionService solutionService) {
        this.service = solutionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        Long taskId = Long.parseLong(request.getParameter("taskId"));
        Long userId = Long.parseLong(request.getParameter("userId"));
        String courseId = request.getParameter("courseId");
        String solutionValue = request.getParameter("solutionValue");
        Solution solution = new Solution(null,taskId,userId, SolutionStatus.SENT,solutionValue,0,"");
        service.commitSolution(solution);
        return CommandResult.redirect(CommandTypes.SHOW_COURSE,"&courseId=",courseId);
    }
}
