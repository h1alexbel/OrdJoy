package com.ordjoy.validation.impl;

import com.ordjoy.entity.Track;
import com.ordjoy.util.LogginUtils;
import com.ordjoy.validation.Error;
import com.ordjoy.validation.RegexBase;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.ordjoy.util.ErrorConstUtils.*;

public class TrackValidator implements Validator<Track> {

    private static final Logger LOGGER = LogManager.getLogger(TrackValidator.class);
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
                LOGGER.log(Level.INFO, LogginUtils.VALIDATION_FAILED, validationResult.getErrors());
            }
            if (!isURLValid(track.getSongUrl())) {
                validationResult.add(Error.of(URL_INVALID, URL_INVALID_MESSAGE));
                LOGGER.log(Level.INFO, LogginUtils.VALIDATION_FAILED, validationResult.getErrors());
            }
        } else {
            validationResult.add(Error.of(TRACK_INVALID, TRACK_IS_INVALID_MESSAGE));
            LOGGER.log(Level.INFO, LogginUtils.VALIDATION_FAILED, validationResult.getErrors());
        }
        return validationResult;
    }

    private boolean isTitleValid(String title) {
        return title != null && title.matches(RegexBase.TITLE_REGEX);
    }

    private boolean isURLValid(String url) {
        return url != null && url.matches(RegexBase.URL_REGEX);
    }
}