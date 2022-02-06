package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dto.TrackDto;
import com.ordjoy.entity.Track;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.TrackService;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.impl.AlbumValidator;
import com.ordjoy.validation.impl.TrackValidator;

import javax.servlet.http.HttpServletRequest;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.*;

public class AddTrackCommand implements FrontCommand {

    private static final String SESSION_TRACK = "track";
    private final TrackService trackService = TrackService.getInstance();
    private final TrackValidator trackValidator = TrackValidator.getInstance();
    private final AlbumValidator albumValidator = AlbumValidator.getInstance();
    private static final String TRACK_TITLE = "trackTitle";
    private static final String TRACK_URL = "trackUrl";
    private static final String ALBUM_TITLE = "albumTitle";

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        String trackTitle = httpServletRequest.getParameter(TRACK_TITLE);
        String trackUrl = httpServletRequest.getParameter(TRACK_URL);
        String albumTitle = httpServletRequest.getParameter(ALBUM_TITLE);
        Track track = trackService.buildTrack(trackUrl, trackTitle, albumTitle);
        ValidationResult validationResult = trackValidator.isValid(track);
        try {
            if (validationResult.isValid()
                && !trackService.isTrackExists(trackTitle)
                && albumValidator.isTitleValid(albumTitle)) {
                TrackDto trackDto = trackService.addNewTrack(track);
                page = httpServletRequest.getContextPath() + JspFormatHelper.getAdminPath(ADMIN_MAIN_PAGE);
                httpServletRequest.getSession().setAttribute(SESSION_TRACK, trackDto);
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