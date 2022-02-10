package com.ordjoy.controller;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandFactory;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.dbmanager.ConnectionPool;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.util.LogginUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/frontController")
public class MainServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(MainServlet.class);
    private static final String FRONT_COMMAND_PARAM = "frontCommand";

    @Override
    public void init() throws ServletException {
        super.init();
        ConnectionPool.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processFrontCommand(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processFrontCommand(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().closeConnectionPool();
    }

    private void processFrontCommand(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        String frontCommand = httpServletRequest.getParameter(FRONT_COMMAND_PARAM);
        LOGGER.log(Level.INFO, LogginUtils.REQUEST_PARAMS, httpServletRequest.getParameterMap());
        FrontCommand command = FrontCommandFactory.getCommand(frontCommand);
        try {
            FrontCommandResult result = command.execute(httpServletRequest);
            LOGGER.log(
                    Level.INFO, LogginUtils.FRONT_COMMAND_RESULT_INFO,
                    result.getPage() + result.getNavigationType());
            switch (result.getNavigationType()) {
                case FORWARD -> getServletContext().getRequestDispatcher(result.getPage()).forward(httpServletRequest, httpServletResponse);
                case REDIRECT -> httpServletResponse.sendRedirect(result.getPage());
                default -> throw new IllegalStateException("Unexpected value: " + result.getNavigationType());
            }
        } catch (ControllerException e) {
            throw new ServletException(e);
        }
    }
}