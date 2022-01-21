package com.ordjoy.command;

import com.ordjoy.exception.ControllerException;

import javax.servlet.http.HttpServletRequest;

public interface FrontCommand {

    FrontCommandResult process(HttpServletRequest httpServletRequest) throws ControllerException;
}