package com.ordjoy.validation.impl;

import com.ordjoy.entity.TrackReview;
import com.ordjoy.util.LogginUtils;
import com.ordjoy.validation.Error;
import com.ordjoy.validation.RegexBase;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.ordjoy.util.ErrorConstUtils.*;

public class TrackReviewValidator implements Validator<TrackReview> {

    private static final Logger LOGGER = LogManager.getLogger(TrackReviewValidator.class);
    private static final TrackReviewValidator INSTANCE = new TrackReviewValidator();

    private TrackReviewValidator() {

    }

    /**
     * @return {@link TrackReviewValidator} instance
     */
    public static TrackReviewValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(TrackReview trackReview) {
        ValidationResult validationResult = new ValidationResult();
        if (trackReview != null) {
            if (!isReviewTextValid(trackReview.getReviewText())) {
                validationResult.add(Error.of(REVIEW_TEXT_INVALID, REVIEW_TEXT_INVALID_MESSAGE));
                LOGGER.info(LogginUtils.VALIDATION_FAILED, validationResult.getErrors());
            }
        } else {
            validationResult.add(Error.of(TRACK_REVIEW_INVALID, TRACK_REVIEW_INVALID_MESSAGE));
            LOGGER.info(LogginUtils.VALIDATION_FAILED, validationResult.getErrors());
        }
        return validationResult;
    }

    private boolean isReviewTextValid(String reviewText) {
        return reviewText != null && reviewText.matches(RegexBase.REVIEW_REGEX);
    }
}