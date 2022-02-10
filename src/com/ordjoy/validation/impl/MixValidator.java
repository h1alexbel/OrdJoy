package com.ordjoy.validation.impl;

import com.ordjoy.entity.Mix;
import com.ordjoy.util.LogginUtils;
import com.ordjoy.validation.Error;
import com.ordjoy.validation.RegexBase;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.ordjoy.util.ErrorConstUtils.*;

public class MixValidator implements Validator<Mix> {

    private static final Logger LOGGER = LogManager.getLogger(MixValidator.class);
    private static final MixValidator INSTANCE = new MixValidator();

    private MixValidator() {

    }

    /**
     * @return {@link MixValidator} instance
     */
    public static MixValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(Mix mix) {
        ValidationResult validationResult = new ValidationResult();
        if (mix != null) {
            if (!isNameValid(mix.getName())) {
                validationResult.add(Error.of(MIX_NAME_INVALID, MIX_NAME_INVALID_MESSAGE));
                LOGGER.log(Level.INFO, LogginUtils.VALIDATION_FAILED, validationResult.getErrors());
            }
            if (!isDescriptionValid(mix.getDescription())) {
                validationResult.add(Error.of(DESCRIPTION_INVALID, DESCRIPTION_INVALID_MESSAGE));
                LOGGER.log(Level.INFO, LogginUtils.VALIDATION_FAILED, validationResult.getErrors());
            }
        } else {
            validationResult.add(Error.of(MIX_INVALID, MIX_INVALID_MESSAGE));
            LOGGER.log(Level.INFO, LogginUtils.VALIDATION_FAILED, validationResult.getErrors());
        }
        return validationResult;
    }

    private boolean isNameValid(String title) {
        return title != null && title.matches(RegexBase.TITLE_REGEX);
    }

    private boolean isDescriptionValid(String description) {
        return description != null && description.matches(RegexBase.DESCRIPTION_REGEX);
    }
}