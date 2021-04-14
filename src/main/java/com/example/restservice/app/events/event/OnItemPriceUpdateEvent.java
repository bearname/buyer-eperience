package com.example.restservice.app.events.event;

import com.example.restservice.app.events.BaseApplicationEvent;
import com.example.restservice.app.model.Subscription;

public class OnItemPriceUpdateEvent extends BaseApplicationEvent {
    private final int oldPrice;

    public OnItemPriceUpdateEvent(Subscription subscription, int oldPrice) {
        super(subscription, "Item Price Update");
        this.oldPrice = oldPrice;
    }

    public int getOldPrice() {
        return oldPrice;
    }
}
