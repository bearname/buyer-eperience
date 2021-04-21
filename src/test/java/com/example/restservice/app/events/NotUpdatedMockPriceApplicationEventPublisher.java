package com.example.restservice.app.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.fail;

public class NotUpdatedMockPriceApplicationEventPublisher implements ApplicationEventPublisher {

    @Override
    public void publishEvent(ApplicationEvent event) {
    }

    @Override
    public void publishEvent(Object event) {
        fail();
    }
}
