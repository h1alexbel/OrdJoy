package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.entity.Mix;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.MixService;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.impl.MixValidator;

import javax.servlet.http.HttpServletRequest;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static com.ordjoy.util.JspPageConst.*;
import static com.ordjoy.util.JspPageConst.ADMIN_DASHBOARD_PAGE;

public class EditMixCommand implements FrontCommand {

    private static final String MIX_NAME = "mixName";
    private static final String MIX_DESCRIPTION = "mixDescription";
    private static final String MIX_ID = "mixId";
    private final MixService mixService = MixService.getInstance();
    private final MixValidator mixValidator = MixValidator.getInstance();

    @Override
    public FrontCommandResult process(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        String mixName = httpServletRequest.getParameter(MIX_NAME);
        String mixDescription = httpServletRequest.getParameter(MIX_DESCRIPTION);
        Long id = Long.valueOf(httpServletRequest.getParameter(MIX_ID));
        Mix mix = mixService.buildMix(mixName, mixDescription);
        mix.setId(id);
        ValidationResult validationResult = mixValidator.isValid(mix);
        try {
            if (validationResult.isValid() && !mixService.isMixExists(mixName)) {
                mixService.updateMix(mix);
                page = httpServletRequest.getContextPath() + JspFormatHelper.getAdminPath(ADMIN_DASHBOARD_PAGE);
            } else {
                page = httpServletRequest.getHeader(REFERER_HEADER);
            }
            frontCommandResult = new FrontCommandResult(page, NavigationType.REDIRECT);
            return frontCommandResult;
        } catch (ServiceException e) {
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
    }
}