package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.util.JspFormatHelper;

import javax.servlet.http.HttpServletRequest;

import static com.ordjoy.util.JspPageConst.INDEX_PAGE;

public class GotoIndexPageCommand implements FrontCommand {

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page = JspFormatHelper.getPublicPath(INDEX_PAGE);
        return new FrontCommandResult(page, NavigationType.REDIRECT);
    }
}