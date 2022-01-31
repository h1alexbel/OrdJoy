package com.ordjoy.command.impl;

import com.ordjoy.command.FrontCommand;
import com.ordjoy.command.FrontCommandResult;
import com.ordjoy.command.NavigationType;
import com.ordjoy.dto.MixDto;
import com.ordjoy.dto.MixReviewDto;
import com.ordjoy.dto.UserAccountDto;
import com.ordjoy.entity.Mix;
import com.ordjoy.entity.MixReview;
import com.ordjoy.entity.UserAccount;
import com.ordjoy.exception.ControllerException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.service.MixService;
import com.ordjoy.service.ReviewService;
import com.ordjoy.service.UserService;
import com.ordjoy.util.JspFormatHelper;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.impl.MixReviewValidator;
import com.ordjoy.validation.impl.MixValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.CONTROLLER_EXCEPTION_MESSAGE;
import static com.ordjoy.util.JspPageConst.*;

public class AddMixReviewCommand implements FrontCommand {

    private static final String MIX_REVIEW_ATTRIBUTE = "mixReview";
    private static final String SESSION_USER = "user";
    private static final String MIX_NAME = "mixName";
    private static final String MIX_REVIEW_TEXT = "mixReviewText";
    private final MixValidator mixValidator = MixValidator.getInstance();
    private final MixService mixService = MixService.getInstance();
    private final ReviewService reviewService = ReviewService.getInstance();
    private final MixReviewValidator mixReviewValidator = MixReviewValidator.getInstance();

    @Override
    public FrontCommandResult process(HttpServletRequest httpServletRequest) throws ControllerException {
        String page;
        FrontCommandResult frontCommandResult;
        UserAccountDto user = (UserAccountDto) httpServletRequest.getSession().getAttribute(SESSION_USER);
        UserService userService = UserService.getInstance();
        UserAccount userAccount = userService.buildUserWithoutPasswordAndAgeFromSession(user);
        String mixName = httpServletRequest.getParameter(MIX_NAME);
        String mixReviewText = httpServletRequest.getParameter(MIX_REVIEW_TEXT);
        try {
            Optional<MixDto> maybeMix = mixService.findMixByMixName(mixName);
            if (maybeMix.isPresent()) {
                MixDto mixDto = maybeMix.get();
                Mix mix = mixService.buildMix(mixDto.getName(), mixDto.getDescription());
                ValidationResult validationResult = mixValidator.isValid(mix);
                if (validationResult.isValid()) {
                    MixReview mixReview = reviewService.buildMixReview(mixReviewText, userAccount, mix);
                    ValidationResult mixReviewValidationResult = mixReviewValidator.isValid(mixReview);
                    if (mixReviewValidationResult.isValid()) {
                        MixReviewDto mixReviewDto = reviewService.addMixReview(mixReview);
                        httpServletRequest.getSession().setAttribute(MIX_REVIEW_ATTRIBUTE, mixReviewDto);
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