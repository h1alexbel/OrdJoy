package com.ordjoy.validation.impl;

import com.ordjoy.entity.MixReview;
import com.ordjoy.validation.Error;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.Validator;

import static com.ordjoy.util.ErrorConstUtils.*;
import static com.ordjoy.util.ErrorConstUtils.MIX_REVIEW_INVALID_MESSAGE;

public class MixReviewValidator implements Validator<MixReview> {

    private static final String REVIEW_REGEX = "^[A-Za-zА-Яа-я].{2,512}";
    private static final MixReviewValidator INSTANCE = new MixReviewValidator();

    private MixReviewValidator() {

    }

    /**
     * @return {@link MixReviewValidator} instance
     */
    public static MixReviewValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(MixReview mixReview) {
        ValidationResult validationResult = new ValidationResult();
        if (mixReview != null) {
            if (!isReviewTextValid(mixReview.getReviewText())) {
                validationResult.add(Error.of(REVIEW_TEXT_INVALID, REVIEW_TEXT_INVALID_MESSAGE));
            }
        } else {
            validationResult.add(Error.of(MIX_REVIEW_INVALID, MIX_REVIEW_INVALID_MESSAGE));
        }
        return validationResult;
    }

    private boolean isReviewTextValid(String reviewText) {
        return reviewText != null && reviewText.matches(REVIEW_REGEX);
    }
}