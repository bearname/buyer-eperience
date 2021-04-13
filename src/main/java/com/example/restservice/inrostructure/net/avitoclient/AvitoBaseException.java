package com.example.restservice.inrostructure.net.avitoclient;

public  class AvitoBaseException extends Exception {
    public AvitoBaseException() {
    }

    public AvitoBaseException(String message) {
        super(message);
    }

    public AvitoBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public AvitoBaseException(Throwable cause) {
        super(cause);
    }

    public AvitoBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
