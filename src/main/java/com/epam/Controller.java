package com.epam;

import com.epam.command.Command;
import com.epam.command.CommandFactory;
import com.epam.command.CommandResult;
import com.epam.connection.ConnectionPool;
import com.epam.exceptions.DaoException;
import com.epam.exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private final CommandFactory commandFactory = new CommandFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            process(request, response);
        } catch (DaoException | ServiceException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            process(request, response);
        } catch (DaoException | ServiceException e) {
            e.printStackTrace();
        }

    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DaoException, ServiceException {
        String commandType = request.getParameter("command");

        Command command = commandFactory.create(commandType);

        CommandResult commandResult = command.execute(request, response);

        String pageName = commandResult.getPageName();

        if (commandResult.isRedirected()) {
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
