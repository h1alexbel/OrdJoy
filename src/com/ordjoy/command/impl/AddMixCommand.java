package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dto.MixDto;
import com.ordjoy.entity.Mix;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.MixService;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.util.LogginUtils;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.impl.MixValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static com.ordjoy.util.JspPageConst.*;

public class AddMixCommand implements FrontCommand {

    private static final Logger LOGGER = LogManager.getLogger(AddMixCommand.class);
    private final MixService mixService = MixService.getInstance();
    private final MixValidator mixValidator = MixValidator.getInstance();
    private static final String SESSION_MIX = "mix";
    private static final String MIX_NAME = "mixName";
    private static final String MIX_DESCRIPTION = "mixDescription";

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        String mixName = httpServletRequest.getParameter(MIX_NAME);
        String mixDescription = httpServletRequest.getParameter(MIX_DESCRIPTION);
        Mix mix = mixService.buildMix(mixName, mixDescription);
        ValidationResult validationResult = mixValidator.isValid(mix);
        try {
            if (validationResult.isValid() && !mixService.isMixExists(mixName)) {
                MixDto mixDto = mixService.addNewMix(mix);
                page = httpServletRequest.getContextPath() + JspFormatHelper.getAdminPath(ADMIN_MAIN_PAGE);
                httpServletRequest.getSession().setAttribute(SESSION_MIX, mixDto);
            } else {
                page = httpServletRequest.getContextPath() + JspFormatHelper.getPublicPath(ERROR_PAGE);
            }
            frontCommandResult = new FrontCommandResult(page, NavigationType.REDIRECT);
        } catch (ServiceException e) {
            LOGGER.warn(LogginUtils.ADD_WARN, e);
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
        return frontCommandResult;
    }
}