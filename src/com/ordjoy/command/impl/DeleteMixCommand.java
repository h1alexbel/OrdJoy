package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.MixService;
import com.ordjoy.util.JspFormatHelper;

import javax.servlet.http.HttpServletRequest;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static com.ordjoy.util.JspPageConst.*;
import static com.ordjoy.util.JspPageConst.REFERER_HEADER;

public class DeleteMixCommand implements FrontCommand {

    private static final String MIX_ID = "mixId";
    private final MixService mixService = MixService.getInstance();

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        Long id = Long.valueOf(httpServletRequest.getParameter(MIX_ID));
        try {
            boolean isDeleted = mixService.deleteMixById(id);
            if (isDeleted) {
                page = httpServletRequest.getHeader(REFERER_HEADER);
            } else {
                page = httpServletRequest.getContextPath() + JspFormatHelper.getAdminPath(ADMIN_MAIN_PAGE);
            }
            frontCommandResult = new FrontCommandResult(page, NavigationType.REDIRECT);
            return frontCommandResult;
        } catch (ServiceException e) {
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
    }
}