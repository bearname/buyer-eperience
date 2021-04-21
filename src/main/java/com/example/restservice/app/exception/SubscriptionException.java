package com.example.restservice.app.exception;

public class SubscriptionException extends Exception {
    public SubscriptionException() {
    }

    public SubscriptionException(String message) {
        super(message);
    }

    public SubscriptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubscriptionException(Throwable cause) {
        super(cause);
    }

    public SubscriptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
