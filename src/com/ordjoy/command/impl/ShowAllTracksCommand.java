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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.*;

public class ShowAllTracksCommand implements FrontCommand {

    private static final String TRACKS_ATTRIBUTE = "tracks";
    private final TrackService trackService = TrackService.getInstance();
    private final TrackFilter defaultTrackFilter = new TrackFilter(20, 0, "");

    @Override
    public FrontCommandResult process(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        try {
            List<TrackDto> tracks = trackService.findAllTracksWithLimitOffset(defaultTrackFilter);
            httpServletRequest.setAttribute(TRACKS_ATTRIBUTE, tracks);
            page = SEPARATOR + JspFormatHelper.getUserPath(TRACK_PAGE);
            frontCommandResult = new FrontCommandResult(page, NavigationType.FORWARD);
        } catch (ServiceException e) {
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
        return frontCommandResult;
    }
}