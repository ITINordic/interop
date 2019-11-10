
package com.itinordic.interop.security;

/**
 *
 * @author Charles Chigoriwa
 */
public class InteropException extends RuntimeException{

    public InteropException() {
    }

    public InteropException(String message) {
        super(message);
    }

    public InteropException(String message, Throwable cause) {
        super(message, cause);
    }

    public InteropException(Throwable cause) {
        super(cause);
    }

    public InteropException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    
    
}
