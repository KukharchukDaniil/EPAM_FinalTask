package com.epam.command;

import com.epam.entities.Solution;
import com.epam.entities.SolutionStatus;
import com.epam.exceptions.ServiceException;
import com.epam.service.SolutionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GradeCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        SolutionService solutionService = new SolutionService();
        Long solutionId = Long.valueOf(request.getParameter("solutionId"));
        Integer solutionMark = Integer.valueOf(request.getParameter("solutionMark"));
        String solutionValue = request.getParameter("solutionValue");
        String solutionComment = request.getParameter("solutionComment");
        Long taskId = Long.valueOf(request.getParameter("taskId"));
        Long userId = Long.valueOf(request.getParameter("userId"));
        Long courseId = Long.valueOf(request.getParameter("courseId"));
        Solution solution = new Solution(
                solutionId, taskId, userId, SolutionStatus.GRADED, solutionValue, solutionMark, solutionComment
        );
        solutionService.commitSolution(solution);
        return CommandResult.redirect(CommandFactory.SHOW_COURSE,"&courseId=", String.valueOf(courseId));
    }
}
