package com.epam.ftask.command;

public class CommandResult {
    private String pageName;
    private boolean isRedirected;

    public CommandResult(String pageName, boolean isRedirected) {
        this.pageName = pageName;
        this.isRedirected = isRedirected;
    }

    public static CommandResult redirect(String pageName) {
        return new CommandResult(pageName, true);
    }
    public static CommandResult redirect(String commandName, String... objects) {
        for (String object : objects) {
            commandName+=object;
        }
        return new CommandResult(commandName, true);
    }
    public static CommandResult forward(String pageName) {
        return new CommandResult(pageName, false);
    }

    public String getPageName() {
        return pageName;
    }

    public boolean isRedirected() {
        return isRedirected;
    }
}
