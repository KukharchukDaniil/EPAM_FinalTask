package com.epam.command;

import com.epam.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        session.setAttribute("user",null);
        session.invalidate();
        return CommandResult.redirect(CommandFactory.SHOW_LOGIN_PAGE);
    }
}
