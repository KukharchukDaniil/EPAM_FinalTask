package com.epam.ftask;

import com.epam.ftask.command.CommandFactory;
import com.epam.ftask.command.Command;
import com.epam.ftask.command.CommandResult;
import com.epam.ftask.command.Destination;
import com.epam.ftask.connection.ConnectionPool;
import com.epam.ftask.exceptions.DaoException;
import com.epam.ftask.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Controller extends HttpServlet {
    public static final String COMMAND = "command";
    public static final String WRONG_COMMAND_NAME = "Wrong command name!";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String CONTROLLER_COMMAND = "/controller?command=";
    private final CommandFactory commandFactory = new CommandFactory();
    private final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageName;
        CommandResult commandResult = null;
        boolean isRedirected = false;
        try {
            String commandType;
            try {
                commandType = request.getParameter(COMMAND);
            } catch (NullPointerException e) {
                LOGGER.error(e.getMessage(), e);
                throw new ServletException(WRONG_COMMAND_NAME);
            }
            Command command = commandFactory.create(commandType);

            commandResult = command.execute(request, response);
            isRedirected = commandResult.isRedirected();
            pageName = commandResult.getPageName();
        } catch (ServiceException | IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
            HttpSession session = request.getSession();
            session.setAttribute(ERROR_MESSAGE, e.getMessage());
            pageName = Destination.ERROR_PAGE;
        }
        if (isRedirected) {
            String context = request.getContextPath();
            String view = context + CONTROLLER_COMMAND + pageName;
            response.sendRedirect(view);
        } else {
            request.getRequestDispatcher(pageName).forward(request, response);
        }


    }


    @Override
    public void destroy() {
        super.destroy();
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connectionPool.closeConnections();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
