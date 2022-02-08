package com.ordjoy.validation.impl;

import com.ordjoy.entity.UserAccount;
import com.ordjoy.validation.Error;
import com.ordjoy.validation.RegexBase;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.Validator;

import static com.ordjoy.util.ErrorConstUtils.*;

public class UserValidator implements Validator<UserAccount> {

    private static final int MIN_AGE_TO_USE_APPLICATION = 13;
    private static final UserValidator INSTANCE = new UserValidator();

    private UserValidator() {

    }

    /**
     * @return {@link UserValidator} instance
     */
    public static UserValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(UserAccount userAccount) {
        ValidationResult validationResult = new ValidationResult();
        if (userAccount != null) {
            if (!isLoginValid(userAccount.getLogin())) {
                validationResult.add(Error.of(LOGIN_INVALID, INVALID_LOGIN_MESSAGE));
            }
            if (!isEmailValid(userAccount.getEmail())) {
                validationResult.add(Error.of(EMAIL_INVALID, INVALID_EMAIL_MESSAGE));
            }
            if (!isFirstNameValid(userAccount.getUserData().getFirstName())) {
                validationResult.add(Error.of(FIRST_NAME_INVALID, INVALID_FIRST_NAME_MESSAGE));
            }
            if (!isLastNameValid(userAccount.getUserData().getLastName())) {
                validationResult.add(Error.of(LAST_NAME_INVALID, INVALID_LAST_NAME_MESSAGE));
            }
            if (!isAgeValid(userAccount.getUserData().getAge())) {
                validationResult.add(Error.of(AGE_INVALID, INVALID_AGE_MESSAGE));
            }
            if (!isCardDataValid(userAccount.getUserData().getCardNumber())) {
                validationResult.add(Error.of(CARD_NUMBER_INVALID, CARD_NUMBER_INVALID_MESSAGE));
            }
        } else {
            validationResult.add(Error.of(USER_INVALID, USER_CANNOT_BE_NULL_MESSAGE));
        }
        return validationResult;
    }

    /** Checks valid or not password is
     * @param password password to validate
     * @return boolean result based on not null check and matching to password regex
     */
    public boolean isPasswordValid(String password) {
        return password != null && password.matches(RegexBase.PASSWORD_REGEX);
    }

    /** Checks valid or not login is
     * @param login login to validate
     * @return boolean result based on not null check and matching to login regex
     */
    public boolean isLoginValid(String login) {
        return login != null && login.matches(RegexBase.LOGIN_REGEX);
    }

    private boolean isCardDataValid(String cardNumber) {
        boolean result = false;
        if (cardNumber != null) {
            result = cardNumber.matches(RegexBase.CARD_NUMBER_REGEX);
        }
        return result;
    }

    private boolean isEmailValid(String email) {
        return email != null && email.matches(RegexBase.EMAIL_REGEX);
    }

    private boolean isFirstNameValid(String firstName) {
        return firstName != null && firstName.matches(RegexBase.FIRST_NAME_REGEX);
    }

    private boolean isLastNameValid(String lastName) {
        return lastName != null && lastName.matches(RegexBase.LAST_NAME_REGEX);
    }

    private boolean isAgeValid(Integer age) {
        return age != null && age > MIN_AGE_TO_USE_APPLICATION;
    }
}