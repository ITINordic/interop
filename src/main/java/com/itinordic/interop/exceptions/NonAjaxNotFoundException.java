package com.itinordic.interop.exceptions;

/**
 *
 * @author Charles Chigoriwa
 */
public class NonAjaxNotFoundException extends RuntimeException {

    public NonAjaxNotFoundException() {
    }

    public NonAjaxNotFoundException(String message) {
        super(message);
    }

    public NonAjaxNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonAjaxNotFoundException(Throwable cause) {
        super(cause);
    }

    public NonAjaxNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
