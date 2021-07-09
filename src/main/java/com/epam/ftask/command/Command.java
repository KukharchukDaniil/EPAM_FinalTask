package com.epam.ftask.command;

import com.epam.ftask.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface Command {
    /**
     *
     * @param request
     * @param response
     * @return {@link com.epam.ftask.command.CommandResult}
     * @throws ServiceException if it occurs
     */
    CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
}
