package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dto.TrackDto;
import com.ordjoy.dto.TrackReviewDto;
import com.ordjoy.dto.UserAccountDto;
import com.ordjoy.entity.Track;
import com.ordjoy.entity.TrackReview;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.ReviewService;
import com.ordjoy.service.TrackService;
import com.ordjoy.service.UserService;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.impl.TrackReviewValidator;
import com.ordjoy.validation.impl.TrackValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.*;

public class AddTrackReviewCommand implements FrontCommand {

    private static final String SESSION_USER = "user";
    private static final String TRACK_REVIEW_ATTRIBUTE = "trackReview";
    private static final String TRACK_TITLE = "trackTitle";
    private static final String TRACK_REVIEW_TEXT = "trackReviewText";
    private final TrackService trackService = TrackService.getInstance();
    private final TrackValidator trackValidator = TrackValidator.getInstance();
    private final ReviewService reviewService = ReviewService.getInstance();
    private final TrackReviewValidator reviewValidator = TrackReviewValidator.getInstance();

    @Override
    public FrontCommandResult process(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        UserAccountDto user = (UserAccountDto) httpServletRequest.getSession().getAttribute(SESSION_USER);
        UserService userService = UserService.getInstance();
        UserAccount userAccount = userService.buildUserWithoutPasswordAndAgeFromSession(user);
        String trackTitle = httpServletRequest.getParameter(TRACK_TITLE);
        String trackReviewText = httpServletRequest.getParameter(TRACK_REVIEW_TEXT);
        try {
            Optional<TrackDto> maybeTrack = trackService.findByTrackTitle(trackTitle);
            if (maybeTrack.isPresent()) {
                TrackDto trackDto = maybeTrack.get();
                Track track = trackService.buildTrack
                        (trackDto.getUrl(), trackDto.getTitle(), trackDto.getAlbum().getTitle());
                ValidationResult validationResult = trackValidator.isValid(track);
                if (validationResult.isValid()) {
                    TrackReview trackReview = reviewService.buildTrackReview(trackReviewText, userAccount, track);
                    ValidationResult reviewValidationResult = reviewValidator.isValid(trackReview);
                    if (reviewValidationResult.isValid()) {
                        TrackReviewDto trackReviewDto = reviewService.addTrackReview(trackReview);
                        httpServletRequest.getSession().setAttribute(TRACK_REVIEW_ATTRIBUTE, trackReviewDto);
                        page = httpServletRequest.getContextPath() + JspFormatHelper.getUserPath(USER_MAIN_PAGE);
                    } else {
                        page = httpServletRequest.getHeader(REFERER_HEADER);
                    }
                } else {
                    page = httpServletRequest.getHeader(REFERER_HEADER);
                }
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