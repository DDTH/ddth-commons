package com.github.ddth.commons.crypto.utils;

/**
 * Thrown by {@link DdthCipherInputStream} and {@link DdthCipherOutputStream} to wrap the actual
 * error while encrypting/decrypting.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.9.2
 */
public class CipherException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CipherException() {
        // TODO Auto-generated constructor stub
    }

    public CipherException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public CipherException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public CipherException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public CipherException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }
}
