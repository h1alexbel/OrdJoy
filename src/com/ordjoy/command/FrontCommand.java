package com.ordjoy.command;

import com.ordjoy.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;

public interface FrontCommand {

    /**
     * Execute {@link FrontCommand}
     * @param httpServletRequest {@link HttpServletRequest} for execute command
     * @return {@link FrontCommandResult} which contains page and navigation type
     * @see NavigationType
     * @throws ControllerException if Service layer have errors
     */
    FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException;
}