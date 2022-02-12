package com.ordjoy.validation;

import com.ordjoy.entity.UserAccount;
import com.ordjoy.entity.UserData;
import com.ordjoy.entity.UserRole;
import com.ordjoy.validation.impl.UserValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidatorTest {

    @Test
    public void isUserValid() {
        UserValidator userValidator = UserValidator.getInstance();
        UserAccount user = UserAccount.builder()
                .login("test")
                .password("pass")
                .email("test@gmail.com")
                .userData(UserData.builder()
                        .firstName("John")
                        .lastName("Dow")
                        .age(18)
                        .cardNumber("1243124355667722")
                        .userRole(UserRole.CLIENT_ROLE)
                        .build())
                .build();
        ValidationResult validationResult = userValidator.isValid(user);
        assertTrue(validationResult.isValid());
    }

    @Test
    public void isUsernameNotEmpty() {
        String login = "test";
        UserValidator userValidator = UserValidator.getInstance();
        boolean actual = userValidator.isLoginValid(login);
        boolean expected = true;
        assertEquals(expected, actual);
        assertTrue(actual, "Username can not be empty!");
    }

    @Test
    public void isUserNameEmpty() {
        String login = "";
        UserValidator userValidator = UserValidator.getInstance();
        boolean actual = userValidator.isLoginValid(login);
        boolean expected = false;
        assertEquals(expected, actual);
        assertFalse(actual, "Username can not be empty!");
    }

    @Test
    public void isPasswordEmpty() {
        String password = "";
        UserValidator userValidator = UserValidator.getInstance();
        boolean actual = userValidator.isPasswordValid(password);
        boolean expected = false;
        assertEquals(expected, actual);
        assertFalse(actual, "Password can not be empty!");
    }

    @Test
    public void isUserValidatorPasswordMethodDoesntThrowException() {
        UserValidator userValidator = UserValidator.getInstance();
        assertDoesNotThrow(() -> userValidator.isPasswordValid(null), "Password can not be null!");
    }

    @Test
    public void isUserValidatorLoginMethodDoesntThrowException() {
        UserValidator userValidator = UserValidator.getInstance();
        assertDoesNotThrow(() -> userValidator.isLoginValid(null), "Login can not be null!");
    }

    @Test
    public void isPasswordNotEmpty() {
        String password = "pass";
        UserValidator userValidator = UserValidator.getInstance();
        boolean actual = userValidator.isPasswordValid(password);
        boolean expected = true;
        assertEquals(expected, actual);
        assertTrue(actual, "Password can not be empty!");
    }

    @Test
    public void isUserValidatorDoesntThrowException() {
        UserValidator userValidator = UserValidator.getInstance();
        assertDoesNotThrow(() -> userValidator.isValid(null), "User can not be null!");
    }

    @Test
    public void ageUnder13Test() {
        UserValidator userValidator = UserValidator.getInstance();
        UserAccount user = UserAccount.builder()
                .login("example")
                .password("pass")
                .email("example@gmail.com")
                .userData(UserData.builder()
                        .firstName("John")
                        .lastName("Dow")
                        .age(13)
                        .cardNumber("1243124355667722")
                        .userRole(UserRole.CLIENT_ROLE)
                        .build())
                .build();
        ValidationResult validationResult = userValidator.isValid(user);
        assertFalse(validationResult.isValid());
    }

    @Test
    public void ageGrater13Test() {
        UserValidator userValidator = UserValidator.getInstance();
        UserAccount user = UserAccount.builder()
                .login("example")
                .password("pass")
                .email("example@gmail.com")
                .userData(UserData.builder()
                        .firstName("John")
                        .lastName("Dow")
                        .age(14)
                        .cardNumber("1243124355667722")
                        .userRole(UserRole.CLIENT_ROLE)
                        .build())
                .build();
        ValidationResult validationResult = userValidator.isValid(user);
        assertTrue(validationResult.isValid());
    }
}