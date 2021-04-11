package com.example.restservice.app.events.event;

import com.example.restservice.app.events.BaseApplicationEvent;
import com.example.restservice.app.model.Subscription;

public class OnNewItemSubscriptionEvent extends BaseApplicationEvent {
    private final String domain;

    public OnNewItemSubscriptionEvent(Subscription subscription, final String domain) {
        super(subscription, "Item Submission Confirmation");
        this.subscription = subscription;
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }
}
