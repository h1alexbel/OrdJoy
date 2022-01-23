package com.ordjoy.validation;

public record Error
        (String code,
         String message) {

    public static Error of(String code, String message) {
        return new Error(code, message);
    }

    @Override
    public String toString() {
        return "Error{" +
               "code='" + code + '\'' +
               ", message='" + message + '\'' +
               '}';
    }
}