package com.epam.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowPageCommand implements  Command{
    private String pageName;

    public ShowPageCommand(String pageName) {
        this.pageName = pageName;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return CommandResult.forward(pageName);
    }
}
