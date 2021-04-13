package com.example.restservice.inrostructure.net.avitoclient;

public class AvitoApiChangedException extends AvitoBaseException {
    public AvitoApiChangedException() {
    }

    public AvitoApiChangedException(String message) {
        super(message);
    }

    public AvitoApiChangedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AvitoApiChangedException(Throwable cause) {
        super(cause);
    }

    public AvitoApiChangedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
