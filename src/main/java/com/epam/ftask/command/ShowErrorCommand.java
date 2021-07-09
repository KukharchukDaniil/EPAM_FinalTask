package com.epam.ftask.command;

import com.epam.ftask.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowErrorCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        return CommandResult.forward(Destination.ERROR_PAGE);
    }
}
