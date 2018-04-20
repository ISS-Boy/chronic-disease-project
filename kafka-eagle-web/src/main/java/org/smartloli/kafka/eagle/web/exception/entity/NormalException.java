package org.smartloli.kafka.eagle.web.exception.entity;

/**
 * Created by dujijun on 2018/4/16.
 */
public class NormalException extends RuntimeException {
    public NormalException() {
        super();
    }

    public NormalException(String message) {
        super(message);
    }

    public NormalException(String message, Throwable cause) {
        super(message, cause);
    }

    public NormalException(Throwable cause) {
        super(cause);
    }

    protected NormalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
