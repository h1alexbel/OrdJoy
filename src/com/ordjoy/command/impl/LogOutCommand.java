package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements FrontCommand {

    private static final String LOGIN_PAGE = "login.jsp";

    @Override
    public FrontCommandResult process(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().invalidate();
        String page = httpServletRequest.getContextPath() + LOGIN_PAGE;
        return new FrontCommandResult(page, NavigationType.REDIRECT);
    }
}