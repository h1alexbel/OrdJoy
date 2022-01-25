package com.ordjoy.util;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.net.BCodec;

public final class PasswordEncoder {

    private static final StringEncoder ENC = new BCodec();

    private PasswordEncoder() {
        throw new UnsupportedOperationException();
    }

    public static String encode(String passwordToEncode) {
        String encodedPassword = passwordToEncode;
        try {
            encodedPassword = ENC.encode(passwordToEncode);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        return encodedPassword;
    }
}