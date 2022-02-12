package com.ordjoy.validation;

import com.ordjoy.entity.Track;
import com.ordjoy.entity.TrackReview;
import com.ordjoy.validation.impl.TrackReviewValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrackReviewValidatorTest {

    @Test
    public void isTrackReviewValidatorDoesntThrowException() {
        TrackReviewValidator trackReviewValidator = TrackReviewValidator.getInstance();
        assertDoesNotThrow(() -> trackReviewValidator.isValid(null),
                "Track Review can be not null!");
    }

    @Test
    public void reviewTextIsEmptyCase() {
        String reviewText = "";
        TrackReviewValidator trackReviewValidator = TrackReviewValidator.getInstance();
        TrackReview trackReview = TrackReview.builder()
                .track(Track.builder()
                        .songUrl("https://www.youtube.com/watch?v=m4racJaB-h4&list=RDm4racJaB-h4&start_radio=1")
                        .title("Test")
                        .build())
                .reviewText(reviewText)
                .build();
        ValidationResult validationResult = trackReviewValidator.isValid(trackReview);
        assertFalse(validationResult.isValid(), "Review Text can not be empty!");
    }

    @Test
    public void reviewTextIsNullCase() {
        TrackReviewValidator trackReviewValidator = TrackReviewValidator.getInstance();
        TrackReview trackReview = TrackReview.builder()
                .track(Track.builder()
                        .songUrl("https://www.youtube.com/watch?v=m4racJaB-h4&list=RDm4racJaB-h4&start_radio=1")
                        .title("Test")
                        .build())
                .reviewText(null)
                .build();
        ValidationResult validationResult = trackReviewValidator.isValid(trackReview);
        assertFalse(validationResult.isValid(), "Review Text can not be null!");
    }
}