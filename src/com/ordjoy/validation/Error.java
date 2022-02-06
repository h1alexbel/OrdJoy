package com.ordjoy.validation;

public record Error
        (String code,
         String message) {

    /**
     * Creates {@link Error} with code and message using static method of
     * @param code code of the {@link Error}
     * @param message detail message of the {@link Error}
     * @return {@link Error} with code and message
     */
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