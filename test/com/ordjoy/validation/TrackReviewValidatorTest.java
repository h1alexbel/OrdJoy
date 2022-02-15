package com.ordjoy.validation;

import com.ordjoy.entity.Track;
import com.ordjoy.entity.TrackReview;
import com.ordjoy.validation.impl.TrackReviewValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class TrackReviewValidatorTest {

    @ParameterizedTest
    @NullSource
    @DisplayName("track review null case test")
    void isTrackReviewValidatorDoesntThrowException(TrackReview trackReview) {
        TrackReviewValidator trackReviewValidator = TrackReviewValidator.getInstance();
        assertDoesNotThrow(() -> trackReviewValidator.isValid(trackReview),
                "Track Review can be not null!");
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("review text null and empty case test")
    void reviewTextIsEmptyAndNullCase(String reviewText) {
        TrackReviewValidator trackReviewValidator = TrackReviewValidator.getInstance();
        TrackReview trackReview = TrackReview.builder()
                .track(Track.builder()
                        .songUrl("https://www.youtube.com/watch?v=m4racJaB-h4&list=RDm4racJaB-h4&start_radio=1")
                        .title("Test")
                        .build())
                .reviewText(reviewText)
                .build();
        ValidationResult validationResult = trackReviewValidator.isValid(trackReview);
        assertFalse(validationResult.isValid(), "Review Text can not be empty or null!");
    }
}