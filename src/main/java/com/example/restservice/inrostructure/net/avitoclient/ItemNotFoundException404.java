package com.example.restservice.inrostructure.net.avitoclient;

public class ItemNotFoundException404 extends AvitoBaseException {
    public ItemNotFoundException404() {
    }

    public ItemNotFoundException404(String message) {
        super(message);
    }

    public ItemNotFoundException404(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemNotFoundException404(Throwable cause) {
        super(cause);
    }

    public ItemNotFoundException404(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
