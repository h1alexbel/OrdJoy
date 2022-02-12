package com.ordjoy.validation;

import com.ordjoy.entity.Mix;
import com.ordjoy.validation.impl.MixValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class MixValidatorTest {

    @ParameterizedTest
    @CsvSource({"Test, Description",
            "Docker, wa2",
            "name2, русс"})
    public void isMixValid(String name, String description) {
        MixValidator mixValidator = MixValidator.getInstance();
        Mix mix = Mix.builder()
                .name(name)
                .description(description)
                .build();
        ValidationResult validationResult = mixValidator.isValid(mix);
        assertTrue(validationResult.isValid());
    }

    @Test
    public void isMixValidatorDoesntThrowException() {
        MixValidator mixValidator = MixValidator.getInstance();
        assertDoesNotThrow(() -> mixValidator.isValid(null));
    }
}