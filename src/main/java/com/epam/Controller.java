package com.epam;

import com.epam.command.CommandFactory;
import com.epam.command.Command;
import com.epam.command.CommandResult;
import com.epam.command.Destination;
import com.epam.connection.ConnectionPool;
import com.epam.exceptions.DaoException;
import com.epam.exceptions.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Controller extends HttpServlet {
    private final CommandFactory commandFactory = new CommandFactory();
    private final Logger LOGGER = LogManager.getLogger(Controller.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageName;
        CommandResult commandResult = null;
        boolean isRedirected = false;
        try {
            String commandType;
            try {
                commandType = request.getParameter("command");
            } catch (NullPointerException e) {
                LOGGER.error(e.getMessage(), e);
                throw new ServletException("Wrong command name!");
            }
            Command command = commandFactory.create(commandType);

            commandResult = command.execute(request, response);
            isRedirected = commandResult.isRedirected();
            pageName = commandResult.getPageName();
        } catch (ServiceException | IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", e.getMessage());
            pageName = Destination.ERROR_PAGE;
        }
        if (isRedirected) {
            String context = request.getContextPath();
            String view = context + "/controller?command=" + pageName;
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
