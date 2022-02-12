package com.ordjoy.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordEncoderTest {

    @Test
    public void encodingTest() {
        String password = "qwerty12345";
        String encodedPassword = PasswordEncoder.encode(password);
        assertNotEquals(password, encodedPassword,
                "Password after encoding should be differ with pre-encoded!");
    }

    @Test
    public void isPasswordEncoderDoesntThrowException() {
        assertDoesNotThrow(() -> PasswordEncoder.encode(null), "");
    }

    @Test
    public void isEncodedPasswordNotEmpty() {
        String password = "qwerty12345";
        String encodedPassword = PasswordEncoder.encode(password);
        assertFalse(encodedPassword.isEmpty());
    }
}