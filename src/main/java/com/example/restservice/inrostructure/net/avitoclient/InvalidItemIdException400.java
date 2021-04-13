package com.example.restservice.inrostructure.net.avitoclient;

public class InvalidItemIdException400 extends AvitoBaseException {
    public InvalidItemIdException400() {
    }

    public InvalidItemIdException400(String message) {
        super(message);
    }

    public InvalidItemIdException400(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidItemIdException400(Throwable cause) {
        super(cause);
    }

    public InvalidItemIdException400(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
