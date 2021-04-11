package com.example.restservice.app.exception;

public class FailedSaveUserException extends Exception {
    public FailedSaveUserException() {
    }

    public FailedSaveUserException(String message) {
        super(message);
    }

    public FailedSaveUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedSaveUserException(Throwable cause) {
        super(cause);
    }

    public FailedSaveUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
