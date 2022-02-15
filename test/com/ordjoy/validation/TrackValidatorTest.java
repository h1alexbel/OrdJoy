package com.ordjoy.validation;

import com.ordjoy.entity.Album;
import com.ordjoy.entity.Track;
import com.ordjoy.validation.impl.TrackValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class TrackValidatorTest {

    @Test
    @DisplayName("track validation test")
    void isTrackValid() {
        TrackValidator trackValidator = TrackValidator.getInstance();
        Track track = Track.builder()
                .title("Test Title")
                .songUrl("https://www.youtube.com/watch?v=m4racJaB-h4&list=RDm4racJaB-h4&start_radio=1")
                .album(Album.builder()
                        .title("Album test title")
                        .build())
                .build();
        ValidationResult validationResult = trackValidator.isValid(track);
        assertTrue(validationResult.isValid());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("track null case test")
    void isTrackValidatorDoesntThrowException(Track track) {
        TrackValidator trackValidator = TrackValidator.getInstance();
        assertDoesNotThrow(() -> trackValidator.isValid(track),
                "Track can not be null!");
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void emptyAndNullTrackTitleCase(String trackTitle) {
        TrackValidator trackValidator = TrackValidator.getInstance();
        Track track = Track.builder()
                .title(trackTitle)
                .songUrl("https://www.youtube.com/watch?v=m4racJaB-h4&list=RDm4racJaB-h4&start_radio=1")
                .build();
        ValidationResult validationResult = trackValidator.isValid(track);
        assertFalse(validationResult.isValid());
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void emptyAndNullTrackURLCase(String trackURL) {
        TrackValidator trackValidator = TrackValidator.getInstance();
        Track track = Track.builder()
                .title("Title")
                .songUrl(trackURL)
                .build();
        ValidationResult validationResult = trackValidator.isValid(track);
        assertFalse(validationResult.isValid());
    }
}