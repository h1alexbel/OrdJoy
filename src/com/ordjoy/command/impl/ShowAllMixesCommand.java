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
import com.ordjoy.util.LogginUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.MIXES_PAGE;
import static com.ordjoy.util.JspPageConst.SEPARATOR;

public class ShowAllMixesCommand implements FrontCommand {

    private static final Logger LOGGER = LogManager.getLogger(ShowAllMixesCommand.class);
    private final MixService mixService = MixService.getInstance();
    private static final String MIXES_ATTRIBUTE = "mixes";
    private static final String OFFSET = "offset";
    private static final int DEFAULT_LIMIT = 20;
    private static final String NO_OF_PAGES = "noOfPages";

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        try {
            int offset = Integer.parseInt(httpServletRequest.getParameter(OFFSET));
            Long records = mixService.getRecords();
            MixFilter mixFilter = new MixFilter(DEFAULT_LIMIT, offset);
            int noOfPages = (int) (records / DEFAULT_LIMIT);
            httpServletRequest.setAttribute(NO_OF_PAGES, noOfPages);
            List<MixDto> mixes = mixService.findAllMixes(mixFilter);
            httpServletRequest.setAttribute(MIXES_ATTRIBUTE, mixes);
            page = SEPARATOR + JspFormatHelper.getUserPath(MIXES_PAGE);
            frontCommandResult = new FrontCommandResult(page, NavigationType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.warn(LogginUtils.SHOW_ALL_WARN, e);
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
        return frontCommandResult;
    }
}