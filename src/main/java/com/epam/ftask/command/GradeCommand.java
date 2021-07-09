package com.epam.ftask.command;

import com.epam.ftask.entities.Solution;
import com.epam.ftask.entities.SolutionStatus;
import com.epam.ftask.exceptions.ServiceException;
import com.epam.ftask.service.SolutionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GradeCommand implements Command {
    private static final String SOLUTION_ID = "solutionId";
    private static final String SOLUTION_MARK = "solutionMark";
    private static final String SOLUTION_VALUE = "solutionValue";
    private static final String SOLUTION_COMMENT = "solutionComment";
    private static final String TASK_ID = "taskId";
    private static final String USER_ID = "userId";
    private static final String COURSE_ID = "courseId";
    private static final String COURSE_ID_PARAMETER = "&courseId=";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        SolutionService solutionService = new SolutionService();
        Long solutionId = Long.valueOf(request.getParameter(SOLUTION_ID));
        Integer solutionMark = Integer.valueOf(request.getParameter(SOLUTION_MARK));
        String solutionValue = InjectionProtector.getSafeAttribute(request, SOLUTION_VALUE);
        String solutionComment = InjectionProtector.getSafeAttribute(request, SOLUTION_COMMENT);
        Long taskId = Long.valueOf(request.getParameter(TASK_ID));
        Long userId = Long.valueOf(request.getParameter(USER_ID));
        Long courseId = Long.valueOf(request.getParameter(COURSE_ID));
        Solution solution = new Solution(
                solutionId, taskId, userId, SolutionStatus.GRADED, solutionValue, solutionMark, solutionComment
        );
        solutionService.commitSolution(solution);
        return CommandResult.redirect(CommandTypes.SHOW_SOLUTIONS, COURSE_ID_PARAMETER, String.valueOf(courseId));
    }
}
