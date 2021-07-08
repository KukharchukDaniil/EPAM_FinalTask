package com.epam.command;

import com.epam.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginErrorCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        request.setAttribute("error", true);
        return CommandResult.forward(Destination.LOGIN_PAGE);
    }
}
