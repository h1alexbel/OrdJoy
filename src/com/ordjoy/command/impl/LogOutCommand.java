package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.util.JspFormatHelper;

import javax.servlet.http.HttpServletRequest;

import static com.ordjoy.util.JspPageConst.*;

public class LogOutCommand implements FrontCommand {

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().invalidate();
        String page = httpServletRequest.getContextPath() + JspFormatHelper.getUserPath(LOGIN_PAGE);
        return new FrontCommandResult(page, NavigationType.REDIRECT);
    }
}