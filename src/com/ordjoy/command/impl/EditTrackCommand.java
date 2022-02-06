package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.entity.Track;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.AlbumService;
import com.ordjoy.service.TrackService;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.impl.AlbumValidator;
import com.ordjoy.validation.impl.TrackValidator;

import javax.servlet.http.HttpServletRequest;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.*;

public class EditTrackCommand implements FrontCommand {

    private static final String ALBUM_ID = "albumId";
    private final TrackService trackService = TrackService.getInstance();
    private final TrackValidator trackValidator = TrackValidator.getInstance();
    private final AlbumValidator albumValidator = AlbumValidator.getInstance();
    private static final String TRACK_TITLE = "trackTitle";
    private static final String TRACK_URL = "trackUrl";
    private static final String ALBUM_TITLE = "albumTitle";
    private static final String TRACK_ID = "trackId";
    private final AlbumService albumService = AlbumService.getInstance();


    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        Long id = Long.valueOf(httpServletRequest.getParameter(TRACK_ID));
        String trackTitle = httpServletRequest.getParameter(TRACK_TITLE);
        String trackUrl = httpServletRequest.getParameter(TRACK_URL);
        String albumTitle = httpServletRequest.getParameter(ALBUM_TITLE);
        Long albumId = Long.valueOf(httpServletRequest.getParameter(ALBUM_ID));
        Track track = trackService.buildTrack(trackUrl, trackTitle, albumTitle);
        track.getAlbum().setId(albumId);
        track.setId(id);
        ValidationResult validationResult = trackValidator.isValid(track);
        try {
            if (validationResult.isValid()
                && !trackService.isTrackExists(trackTitle)
                && albumValidator.isTitleValid(albumTitle)
                && albumService.isAlbumExists(albumTitle)) {
                trackService.updateTrack(track);
                page = httpServletRequest.getContextPath() + JspFormatHelper.getAdminPath(ADMIN_DASHBOARD_PAGE);
            } else {
                page = httpServletRequest.getHeader(REFERER_HEADER);
            }
            frontCommandResult = new FrontCommandResult(page, NavigationType.REDIRECT);
        } catch (ServiceException e) {
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
        return frontCommandResult;
    }
}