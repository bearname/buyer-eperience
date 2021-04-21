package com.example.restservice.app.events;

import com.example.restservice.app.events.event.OnItemPriceUpdateEvent;
import com.example.restservice.app.model.Item;
import com.example.restservice.app.model.Subscription;
import com.example.restservice.app.model.User;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigInteger;

import static com.example.restservice.app.repository.MockSubscriptionRepository.ITEMS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MockPriceApplicationEventPublisher implements ApplicationEventPublisher {
    @Override
    public void publishEvent(ApplicationEvent event) {
        if (event instanceof OnItemPriceUpdateEvent) {
            final Subscription subscription = ((OnItemPriceUpdateEvent) event).getSubscription();
            final User eventUser = subscription.getUser();
            final Item resultItem = subscription.getItem();
            final User user = new User(BigInteger.valueOf(1), "test@email.com", true);
            final Item item2 = ITEMS.get(0);
            assertEquals(eventUser.getId(), user.getId());
            assertEquals(eventUser.getEmail(), user.getEmail());
            assertEquals(resultItem.getId(), item2.getId());
            assertEquals(2501, resultItem.getPrice());
        } else {
            fail();
        }
    }

    @Override
    public void publishEvent(Object event) {

    }
}
