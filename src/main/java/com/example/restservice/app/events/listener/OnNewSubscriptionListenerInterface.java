package com.example.restservice.app.events.listener;

import com.example.restservice.app.events.event.OnNewItemSubscriptionEvent;
import org.springframework.context.ApplicationListener;

public interface OnNewSubscriptionListenerInterface extends ApplicationListener<OnNewItemSubscriptionEvent> {
}
