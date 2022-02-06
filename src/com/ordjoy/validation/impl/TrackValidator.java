package com.ordjoy.validation.impl;

import com.ordjoy.entity.Track;
import com.ordjoy.validation.Error;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.Validator;

import static com.ordjoy.util.ErrorConstUtils.*;

public class TrackValidator implements Validator<Track> {

    private static final String TITLE_REGEX = "^\\w.{1,512}.$";
    private static final String URL_REGEX = "^https://\\w.+$";
    private static final TrackValidator INSTANCE = new TrackValidator();

    private TrackValidator() {

    }

    /**
     * @return {@link TrackValidator} instance
     */
    public static TrackValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(Track track) {
        ValidationResult validationResult = new ValidationResult();
        if (track != null) {
            if (!isTitleValid(track.getTitle())) {
                validationResult.add(Error.of(TITLE_INVALID, TRACK_TITLE_INVALID_MESSAGE));
            }
            if (!isURLValid(track.getSongUrl())) {
                validationResult.add(Error.of(URL_INVALID, URL_INVALID_MESSAGE));
            }
        } else {
            validationResult.add(Error.of(TRACK_INVALID, TRACK_IS_INVALID_MESSAGE));
        }
        return validationResult;
    }

    private boolean isTitleValid(String title) {
        return title != null && title.matches(TITLE_REGEX);
    }

    private boolean isURLValid(String url) {
        return url != null && url.matches(URL_REGEX);
    }
}