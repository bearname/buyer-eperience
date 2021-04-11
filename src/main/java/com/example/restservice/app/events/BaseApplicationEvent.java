package com.example.restservice.app.events;

import com.example.restservice.app.model.Subscription;
import org.springframework.context.ApplicationEvent;

public abstract class BaseApplicationEvent extends ApplicationEvent {

    protected final String subject;
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     * which the event is associated (never {@code null})
     */
    protected Subscription subscription;

    protected BaseApplicationEvent(Subscription subscription, String subject) {
        super(subscription);
        this.subscription = subscription;
        this.subject = subject;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }


    public String getSubject() {
        return subject;
    }
}
