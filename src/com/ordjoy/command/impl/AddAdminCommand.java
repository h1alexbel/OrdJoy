package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dto.UserAccountDto;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.UserService;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.util.PasswordEncoder;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.impl.UserValidator;

import javax.servlet.http.HttpServletRequest;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.*;

public class AddAdminCommand implements FrontCommand {

    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String AGE = "age";
    private static final String CARD_NUMBER = "cardNumber";
    private static final String SESSION_USER = "user";
    private final UserValidator userValidator = UserValidator.getInstance();
    private final UserService userService = UserService.getInstance();

    @Override
    public FrontCommandResult process(HttpServletRequest httpServletRequest) throws ControllerException {
        FrontCommandResult frontCommandResult;
        String page;
        String email = httpServletRequest.getParameter(EMAIL);
        String firstName = httpServletRequest.getParameter(FIRST_NAME);
        String lastName = httpServletRequest.getParameter(LAST_NAME);
        String login = httpServletRequest.getParameter(LOGIN);
        String password = httpServletRequest.getParameter(PASSWORD);
        String age = httpServletRequest.getParameter(AGE);
        String cardNumber = httpServletRequest.getParameter(CARD_NUMBER);
        try {
            String encodedPassword = PasswordEncoder.encode(password);
            UserAccount admin = userService.buildAdmin
                    (email, login, encodedPassword, firstName, lastName, age, cardNumber);
            ValidationResult validationResult = userValidator.isValid(admin);
            if (validationResult.isValid() && !userService.isLoginExists(login)) {
                UserAccountDto adminAccountDto = userService.saveNewUser(admin);
                page = httpServletRequest.getContextPath() + JspFormatHelper.getAdminPath(ADMIN_MAIN_PAGE);
                httpServletRequest.getSession().setAttribute(SESSION_USER, adminAccountDto);
            } else {
                page = httpServletRequest.getContextPath() + JspFormatHelper.getPublicPath(ERROR_PAGE);
            }
            frontCommandResult = new FrontCommandResult(page, NavigationType.REDIRECT);
        } catch (ServiceException e) {
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
        return frontCommandResult;
    }
}