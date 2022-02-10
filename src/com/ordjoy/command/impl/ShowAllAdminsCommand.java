package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dao.filter.UserAccountFilter;
import com.ordjoy.dto.UserAccountDto;
import com.ordjoy.entity.UserRole;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.UserService;
import com.ordjoy.util.JspFormatHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.*;

public class ShowAllAdminsCommand implements FrontCommand {

    private static final int DEFAULT_LIMIT = 20;
    private static final String OFFSET = "offset";
    private static final String NO_OF_PAGES = "noOfPages";
    private final UserService userService = UserService.getInstance();
    private static final String USERS_ATTRIBUTE = "users";

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        try {
            int offset = Integer.parseInt(httpServletRequest.getParameter(OFFSET));
            Long records = userService.getAdminRoleRecords();
            UserAccountFilter userAccountFilter = new UserAccountFilter(DEFAULT_LIMIT, offset, UserRole.ADMIN_ROLE);
            List<UserAccountDto> users = userService.findAllUsersWithLimitOffset(userAccountFilter);
            int noOfPages = (int) (records / DEFAULT_LIMIT);
            httpServletRequest.setAttribute(NO_OF_PAGES, noOfPages);
            httpServletRequest.setAttribute(USERS_ATTRIBUTE, users);
            page = SEPARATOR + JspFormatHelper.getAdminPath(ALL_ADMINS_PAGE);
            frontCommandResult = new FrontCommandResult(page, NavigationType.FORWARD);
        } catch (ServiceException e) {
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
        return frontCommandResult;
    }
}