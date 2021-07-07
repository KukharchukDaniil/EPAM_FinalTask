package com.epam.command;

import com.epam.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetLocaleCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String lang = request.getParameter("lang");
        session.setAttribute("locale",lang);
        return CommandResult.redirect(CommandTypes.SHOW_MAIN_PAGE);
    }
}
