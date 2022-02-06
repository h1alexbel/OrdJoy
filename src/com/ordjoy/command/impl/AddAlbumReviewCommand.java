package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dto.AlbumDto;
import com.ordjoy.dto.AlbumReviewDto;
import com.ordjoy.dto.UserAccountDto;
import com.ordjoy.entity.Album;
import com.ordjoy.entity.AlbumReview;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.AlbumService;
import com.ordjoy.service.ReviewService;
import com.ordjoy.service.UserService;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.impl.AlbumReviewValidator;
import com.ordjoy.validation.impl.AlbumValidator;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.USER_MAIN_PAGE;

public class AddAlbumReviewCommand implements FrontCommand {

    private static final String SESSION_USER = "user";
    private static final String ALBUM_TITLE = "albumTitle";
    private static final String ALBUM_REVIEW_TEXT = "albumReviewText";
    private static final String ALBUM_REVIEW_ATTRIBUTE = "albumReview";
    private static final String REFERER_HEADER = "Referer";
    private final ReviewService reviewService = ReviewService.getInstance();
    private final AlbumReviewValidator albumReviewValidator = AlbumReviewValidator.getInstance();
    private final AlbumService albumService = AlbumService.getInstance();
    private final AlbumValidator albumValidator = AlbumValidator.getInstance();

    @Override
    public FrontCommandResult execute(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        UserAccountDto user = (UserAccountDto) httpServletRequest.getSession().getAttribute(SESSION_USER);
        UserService userService = UserService.getInstance();
        String albumTitle = httpServletRequest.getParameter(ALBUM_TITLE);
        String albumReviewText = httpServletRequest.getParameter(ALBUM_REVIEW_TEXT);
        try {
            if (albumValidator.isTitleValid(albumTitle)) {
                Optional<AlbumDto> maybeAlbum = albumService.findAlbumByTitle(albumTitle);
                if (maybeAlbum.isPresent()) {
                    UserAccount userAccount = userService.buildUserAccountFromSession(user);
                    Album album = albumService.buildAlbum(albumTitle);
                    AlbumReview albumReview = reviewService.buildAlbumReview(albumReviewText, userAccount, album);
                    ValidationResult validationResult = albumReviewValidator.isValid(albumReview);
                    if (validationResult.isValid()) {
                        AlbumReviewDto albumReviewDto = reviewService.addAlbumReview(albumReview);
                        httpServletRequest.getSession().setAttribute(ALBUM_REVIEW_ATTRIBUTE, albumReviewDto);
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