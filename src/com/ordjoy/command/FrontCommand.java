package com.ordjoy.command;

import javax.servlet.http.HttpServletRequest;

public interface FrontCommand {

    FrontCommandResult process(HttpServletRequest httpServletRequest);
}