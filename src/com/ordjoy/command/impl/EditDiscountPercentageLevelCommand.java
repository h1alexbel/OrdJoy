package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.UserService;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.util.LogginUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static com.ordjoy.util.JspPageConst.*;

public class EditDiscountPercentageLevelCommand implements FrontCommand {

    private static final Logger LOGGER = LogManager.getLogger(EditDiscountPercentageLevelCommand.class);
    private final UserService userService = UserService.getInstance();
    private static final String EMAIL = "email";
    private static final String DISCOUNT_PERCENTAGE_LEVEL = "discountPercentageLevel";

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        String userEmail = httpServletRequest.getParameter(EMAIL);
        Integer discountPercentageLevel =
                Integer.valueOf(httpServletRequest.getParameter(DISCOUNT_PERCENTAGE_LEVEL));
        try {
            userService.addDiscountPercentageLevel(discountPercentageLevel, userEmail);
            page = httpServletRequest.getContextPath() + JspFormatHelper.getAdminPath(ADMIN_DASHBOARD_PAGE);
            frontCommandResult = new FrontCommandResult(page, NavigationType.REDIRECT);
            return frontCommandResult;
        } catch (ServiceException e) {
            LOGGER.warn(LogginUtils.EDIT_WARN, e);
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
    }
}