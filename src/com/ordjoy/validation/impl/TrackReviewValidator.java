package com.ordjoy.validation.impl;

import com.ordjoy.entity.TrackReview;
import com.ordjoy.validation.Error;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.Validator;

import static com.ordjoy.util.ErrorConstUtils.*;

public class TrackReviewValidator implements Validator<TrackReview> {

    private static final String REVIEW_REGEX = "^\\w.{2,512}";
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
            }
        } else {
            validationResult.add(Error.of(TRACK_REVIEW_INVALID, TRACK_REVIEW_INVALID_MESSAGE));
        }
        return validationResult;
    }

    private boolean isReviewTextValid(String reviewText) {
        return reviewText != null && reviewText.matches(REVIEW_REGEX);
    }
}