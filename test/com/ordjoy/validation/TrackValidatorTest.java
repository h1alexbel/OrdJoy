package com.ordjoy.validation;

import com.ordjoy.entity.Album;
import com.ordjoy.entity.Track;
import com.ordjoy.validation.impl.TrackValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrackValidatorTest {

    @Test
    public void isTrackValid() {
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

    @Test
    public void isTrackValidatorDoesntThrowException() {
        TrackValidator trackValidator = TrackValidator.getInstance();
        assertDoesNotThrow(() -> trackValidator.isValid(null),
                "Track can not be null!");
    }

    @Test
    public void emptyTrackTitleCase() {
        String trackTitle = "";
        TrackValidator trackValidator = TrackValidator.getInstance();
        Track track = Track.builder()
                .title(trackTitle)
                .songUrl("https://www.youtube.com/watch?v=m4racJaB-h4&list=RDm4racJaB-h4&start_radio=1")
                .build();
        ValidationResult validationResult = trackValidator.isValid(track);
        assertFalse(validationResult.isValid());
    }

    @Test
    public void emptyTrackURLCase() {
        String trackURL = "";
        TrackValidator trackValidator = TrackValidator.getInstance();
        Track track = Track.builder()
                .title("Title")
                .songUrl(trackURL)
                .build();
        ValidationResult validationResult = trackValidator.isValid(track);
        assertFalse(validationResult.isValid());
    }

    @Test
    public void nullTrackTitleCase() {
        TrackValidator trackValidator = TrackValidator.getInstance();
        Track track = Track.builder()
                .title(null)
                .songUrl("https://www.youtube.com/watch?v=m4racJaB-h4&list=RDm4racJaB-h4&start_radio=1")
                .build();
        ValidationResult validationResult = trackValidator.isValid(track);
        assertFalse(validationResult.isValid());
    }

    @Test
    public void nullTrackURLCase() {
        TrackValidator trackValidator = TrackValidator.getInstance();
        Track track = Track.builder()
                .title("Title")
                .songUrl(null)
                .build();
        ValidationResult validationResult = trackValidator.isValid(track);
        assertFalse(validationResult.isValid());
    }
}