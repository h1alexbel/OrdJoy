package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dto.MixDto;
import com.ordjoy.dto.TrackDto;
import com.ordjoy.entity.Mix;
import com.ordjoy.entity.Track;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.MixService;
import com.ordjoy.service.TrackService;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.impl.MixValidator;
import com.ordjoy.validation.impl.TrackValidator;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static com.ordjoy.util.JspPageConst.*;

public class AddTrackToMixCommand implements FrontCommand {

    private static final String MIX_NAME = "mixName";
    private static final String TRACK_TITLE = "trackTitle";
    private final TrackService trackService = TrackService.getInstance();
    private final TrackValidator trackValidator = TrackValidator.getInstance();
    private final MixService mixService = MixService.getInstance();
    private final MixValidator mixValidator = MixValidator.getInstance();

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        String trackTitle = httpServletRequest.getParameter(TRACK_TITLE);
        String mixName = httpServletRequest.getParameter(MIX_NAME);
        try {
            Optional<TrackDto> maybeTrack = trackService.findByTrackTitle(trackTitle);
            if (maybeTrack.isPresent()) {
                TrackDto trackDto = maybeTrack.get();
                Track track = trackService.buildTrack(trackDto.getUrl(), trackDto.getTitle(), trackDto.getAlbum().getTitle());
                ValidationResult validationResult = trackValidator.isValid(track);
                if (validationResult.isValid()) {
                    Optional<MixDto> maybeMix = mixService.findMixByMixName(mixName);
                    if (maybeMix.isPresent()) {
                        MixDto mixDto = maybeMix.get();
                        Mix mix = mixService.buildMix(mixDto.getName(), mixDto.getDescription());
                        ValidationResult mixValidationResult = mixValidator.isValid(mix);
                        if (mixValidationResult.isValid()) {
                            trackService.addExistingTrackToMix(mix, track);
                            page = httpServletRequest.getContextPath() + JspFormatHelper.getAdminPath(ADMIN_MAIN_PAGE);
                        } else {
                            page = httpServletRequest.getContextPath() + JspFormatHelper.getPublicPath(ERROR_PAGE);
                        }
                    } else {
                        page = httpServletRequest.getContextPath() + JspFormatHelper.getPublicPath(ERROR_PAGE);
                    }
                } else {
                    page = httpServletRequest.getContextPath() + JspFormatHelper.getPublicPath(ERROR_PAGE);
                }
            } else {
                page = httpServletRequest.getContextPath() + JspFormatHelper.getPublicPath(ERROR_PAGE);
            }
            frontCommandResult = new FrontCommandResult(page, NavigationType.REDIRECT);
            return frontCommandResult;
        } catch (ServiceException e) {
            throw new ControllerException(CONTROLLER_EXCEPTION_MESSAGE, e);
        }
    }
}