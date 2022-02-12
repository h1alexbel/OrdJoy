package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dao.filter.TrackFilter;
import com.ordjoy.dto.TrackDto;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.TrackService;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.util.LogginUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.*;

public class ShowAllTracksCommand implements FrontCommand {

    private static final Logger LOGGER = LogManager.getLogger(ShowAllTracksCommand.class);
    private final TrackService trackService = TrackService.getInstance();
    private static final String TRACKS_ATTRIBUTE = "tracks";
    private static final String OFFSET = "offset";
    private static final int DEFAULT_LIMIT = 20;
    private static final String NO_OF_PAGES = "noOfPages";
    private static final String ALBUM_TITLE = "";

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        try {
            int offset = Integer.parseInt(httpServletRequest.getParameter(OFFSET));
            Long records = trackService.getRecords();
            TrackFilter trackFilter = new TrackFilter(DEFAULT_LIMIT, offset, ALBUM_TITLE);
            List<TrackDto> tracks = trackService.findAllTracksWithLimitOffset(trackFilter);
            int noOfPages = (int) (records / DEFAULT_LIMIT);
            httpServletRequest.setAttribute(NO_OF_PAGES, noOfPages);
            httpServletRequest.setAttribute(TRACKS_ATTRIBUTE, tracks);
            page = SEPARATOR + JspFormatHelper.getUserPath(TRACK_PAGE);
            frontCommandResult = new FrontCommandResult(page, NavigationType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.warn(LogginUtils.SHOW_ALL_WARN, e);
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
        return frontCommandResult;
    }
}