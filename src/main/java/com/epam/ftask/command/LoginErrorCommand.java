package com.epam.ftask.command;

import com.epam.ftask.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginErrorCommand implements Command{

    private static final String ERROR = "error";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        request.setAttribute(ERROR, true);
        return CommandResult.forward(Destination.LOGIN_PAGE);
    }
}
