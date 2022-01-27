package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dto.UserAccountDto;
import com.ordjoy.entity.UserRole;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.UserService;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.util.PasswordEncoder;
import com.ordjoy.validation.impl.UserValidator;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static com.ordjoy.util.JspPageConst.*;

public class LoginCommand implements FrontCommand {

    private static final String LOGIN_USERNAME = "login";
    private static final String PASSWORD = "password";
    private static final String SESSION_USER = "user";
    private final UserValidator userValidator = UserValidator.getInstance();
    private final UserService userService = UserService.getInstance();

    @Override
    public FrontCommandResult process(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult = null;
        String login = httpServletRequest.getParameter(LOGIN_USERNAME);
        String password = httpServletRequest.getParameter(PASSWORD);
        if (userValidator.isLoginValid(login) && userValidator.isPasswordValid(password)) {
            String encoded = PasswordEncoder.encode(password);
            try {
                Optional<UserAccountDto> maybeUser = userService.findUserByLoginAndPassword(login, encoded);
                if (maybeUser.isPresent()) {
                    UserAccountDto userAccountDto = maybeUser.get();
                    UserRole role = userAccountDto.getRole();
                    httpServletRequest.getSession().setAttribute(SESSION_USER, userAccountDto);
                    switch (role) {
                        case CLIENT_ROLE -> page = httpServletRequest.getContextPath() + JspFormatHelper.getAdminPath(USER_MAIN_PAGE);
                        case ADMIN_ROLE -> page = httpServletRequest.getContextPath() + JspFormatHelper.getAdminPath(ADMIN_MAIN_PAGE);
                        default -> throw new IllegalStateException("Unexpected value: " + role);
                    }
                    frontCommandResult = new FrontCommandResult(page, NavigationType.REDIRECT);
                } else {
                    page = JspFormatHelper.getPublicPath(LOGIN_PAGE);
                    frontCommandResult = new FrontCommandResult(page, NavigationType.REDIRECT);
                }
            } catch (ServiceException e) {
                throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
            }
        } else {
            page = JspFormatHelper.getPublicPath(LOGIN_PAGE);
            frontCommandResult = new FrontCommandResult(page, NavigationType.REDIRECT);
        }
        return frontCommandResult;
    }
}