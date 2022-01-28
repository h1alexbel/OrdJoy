package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dao.filter.UserAccountFilter;
import com.ordjoy.dto.UserAccountDto;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.UserService;
import com.ordjoy.util.ExceptionMessageUtils;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.util.JspPageConst;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowAllUsersCommand implements FrontCommand {

    private static final String USERS_ATTRIBUTE = "users";
    private final UserService userService = UserService.getInstance();
    private final UserAccountFilter userAccountFilter = new UserAccountFilter(20, 0);

    @Override
    public FrontCommandResult process(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        try {
            List<UserAccountDto> users = userService.findAllUsersWithLimitOffset(userAccountFilter);
            httpServletRequest.setAttribute(USERS_ATTRIBUTE, users);
            page = "/" + JspFormatHelper.getAdminPath(JspPageConst.ALL_USERS_PAGE);
            frontCommandResult = new FrontCommandResult(page, NavigationType.FORWARD);
        } catch (ServiceException e) {
            throw new ControllerException(ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE, e);
        }
        return frontCommandResult;
    }
}