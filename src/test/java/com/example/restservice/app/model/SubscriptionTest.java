package com.example.restservice.app.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SubscriptionTest {
    @Test
    public void test() {

        final User user = new User(1L, "test@mail.ru");
        final Item item = new Item();
        final Subscription subscription = new Subscription(item, user);
        final Subscription subscription1 = new Subscription(item, user);
        assertNotEquals(subscription1, subscription);
    }

    @Test
    public void test1() {
        final User user = new User(1L, "test@mail.ru");
        final User user1 = new User(2L, "test@mail.ru");
        final Item item = new Item();
        final Subscription subscription = new Subscription(item, user);
        final Subscription subscription1 = new Subscription(item, user1);
        assertNotEquals(subscription1, subscription);
    }

    @Test
    public void test2() {
        final User user = new User(1L, "test@mail.ru");
        final User user1 = new User(2L, "test@mail.ru");
        final Item item = new Item();
        final Subscription subscription = new Subscription(item, user);
        subscription.setItem(new Item("23", 23, "https://avito.url.com/asdf?sadfasdf=asdf"));
        subscription.setUser(user1);
        final Subscription subscription1 = new Subscription(item, user1);
        assertNotEquals(subscription1, subscription);
    }

    @Test
    public void test3() {
        final User user = new User(1L, "test@mail.ru");
        final User user1 = new User(2L, "test@mail.ru");
        final Item item = new Item();
        final Subscription subscription = new Subscription(item, user);
        subscription.setItem(new Item("23", 23, "https://avito.url.com/asdf?sadfasdf=asdf"));
        final Subscription subscription1 = new Subscription(item, user1);
        assertNotEquals(subscription1, subscription);
    }

    @Test
    public void testToString() {
        final User user = new User(1L, "test@mail.ru");
        final Item item = new Item("23", 23, "https://avito.url.com/asdf?sadfasdf=asdf");
        final Subscription subscription = new Subscription(item, user);
        assertEquals("Subscription{" +
                "item=" + item +
                ", user=" + user +
                ", isActive=" + true +
                ", isVerified=" + false +
                '}', subscription.toString());
    }

}