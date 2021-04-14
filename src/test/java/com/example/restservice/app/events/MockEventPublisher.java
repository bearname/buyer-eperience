package com.example.restservice.app.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public class  MockEventPublisher implements ApplicationEventPublisher {
    @Override
    public void publishEvent(ApplicationEvent event) {

    }

    @Override
    public void publishEvent(Object event) {

    }
}
