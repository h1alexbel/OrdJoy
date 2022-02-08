package com.ordjoy.validation;

public final class RegexBase {

    private RegexBase() {
        throw new UnsupportedOperationException();
    }

    public static final String LOGIN_REGEX = "^[A-Za-zА-Яа-я1-9]{3,20}$";
    public static final String EMAIL_REGEX = "^[A-Za-z.]+\\w+@[A-Za-z]{2,}\\.(com|org)$";
    public static final String PASSWORD_REGEX = "^\\w{2,64}$";
    public static final String FIRST_NAME_REGEX = "^[A-Za-zА-Яа-я]{2,20}$";
    public static final String LAST_NAME_REGEX = "^[A-Za-zА-Яа-я]{2,20}$";
    public static final String CARD_NUMBER_REGEX = "^\\d{16}$";
    public static final String TITLE_REGEX = "^[A-Za-zА-Яа-я].{1,512}.$";
    public static final String URL_REGEX = "^https://\\w.+$";
    public static final String REVIEW_REGEX = "^[A-Za-zА-Яа-я].{2,512}";
    public static final String PRICE_REGEX = "^\\d{1,}$";
    public static final String DESCRIPTION_REGEX = "[A-Za-zА-Яа-я].{2,512}";
}