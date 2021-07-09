package com.epam.ftask.command;

import com.epam.ftask.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetLocaleCommand implements Command{

    private static final String LANGUAGE = "lang";
    private static final String LOCALE = "locale";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String lang = request.getParameter(LANGUAGE);
        session.setAttribute(LOCALE,lang);
        return CommandResult.redirect(CommandTypes.SHOW_MAIN_PAGE);
    }
}
