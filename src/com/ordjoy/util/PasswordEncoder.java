package com.ordjoy.util;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.net.BCodec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PasswordEncoder {

    private static final Logger LOGGER = LogManager.getLogger(PasswordEncoder.class);
    private static final StringEncoder ENC = new BCodec();

    private PasswordEncoder() {
        throw new UnsupportedOperationException();
    }

    /**
     * Encode incoming password
     *
     * @param passwordToEncode password that be encoded
     * @return encoded password
     * @see BCodec
     */
    public static String encode(String passwordToEncode) {
        String encodedPassword = passwordToEncode;
        try {
            encodedPassword = ENC.encode(passwordToEncode);
        } catch (EncoderException e) {
            LOGGER.error(LogginUtils.PASSWORD_ENCODING_ERROR, e);
        }
        return encodedPassword;
    }
}