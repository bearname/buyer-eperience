package com.example.restservice.app.service.mock;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.fail;

public class MockApplicationEventPublisher implements ApplicationEventPublisher {
    @Override
    public void publishEvent(ApplicationEvent event) {
        fail();
    }

    @Override
    public void publishEvent(Object event) {
        fail();
    }
}
