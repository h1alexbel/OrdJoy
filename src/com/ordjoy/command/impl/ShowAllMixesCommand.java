package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dao.filter.MixFilter;
import com.ordjoy.dto.MixDto;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.MixService;
import com.ordjoy.util.JspFormatHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.MIXES_PAGE;
import static com.ordjoy.util.JspPageConst.SEPARATOR;

public class ShowAllMixesCommand implements FrontCommand {

    private static final String MIXES_ATTRIBUTE = "mixes";
    private final MixService mixService = MixService.getInstance();
    private final MixFilter defaultMixFilter = new MixFilter(20, 0);

    @Override
    public FrontCommandResult process(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        try {
            List<MixDto> mixes = mixService.findAllMixes(defaultMixFilter);
            httpServletRequest.setAttribute(MIXES_ATTRIBUTE, mixes);
            page = SEPARATOR + JspFormatHelper.getUserPath(MIXES_PAGE);
            frontCommandResult = new FrontCommandResult(page, NavigationType.FORWARD);
        } catch (ServiceException e) {
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
        return frontCommandResult;
    }
}