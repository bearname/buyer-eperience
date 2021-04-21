package com.example.restservice.app.service;

import com.example.restservice.app.events.event.OnItemPriceUpdateEvent;
import com.example.restservice.app.events.event.OnNewItemSubscriptionEvent;
import com.example.restservice.app.model.Item;
import com.example.restservice.app.model.Subscription;
import com.example.restservice.app.model.User;
import com.example.restservice.app.service.mock.MockJavaMailSender;
import com.example.restservice.inrostructure.config.Config;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;

import java.math.BigInteger;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JavaMailSenderMailerServiceTest {
    @Test
    void netItem() {
        final MockJavaMailSender javaMailSender = new MockJavaMailSender();

        final JavaMailSenderMailerService mailerService = new JavaMailSenderMailerService(javaMailSender);
        final Item item = new Item("2112597286", 25000, "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286");
        final User user = new User(BigInteger.valueOf(1L), "test@mail.com");
        final Subscription subscription = new Subscription(item, user);
        final OnNewItemSubscriptionEvent event = new OnNewItemSubscriptionEvent(subscription, "localhost:8080");
        final String text = "new subscription. please confirm your email";
        mailerService.send(event, text);
        final SimpleMailMessage message = javaMailSender.getMessage();
        assertEquals(Config.FROM_EMAIL, message.getFrom());
        assertEquals(user.getEmail(), Objects.requireNonNull(message.getTo())[0]);
        assertEquals("Item Submission Confirmation", message.getSubject());
        assertEquals(text, message.getText());
    }

    @Test
    void priceUpdate() {
        final MockJavaMailSender javaMailSender = new MockJavaMailSender();

        final JavaMailSenderMailerService mailerService = new JavaMailSenderMailerService(javaMailSender);
        final Item item = new Item("2112597286", 25000, "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286");
        final User user = new User(BigInteger.valueOf(1L), "test@mail.com");
        final Subscription subscription = new Subscription(item, user);
        final OnItemPriceUpdateEvent event = new OnItemPriceUpdateEvent(subscription, 30000);
        final String text = "item price updated. new price " + item.getPrice();

        mailerService.send(event, text);
        final SimpleMailMessage message = javaMailSender.getMessage();

        assertEquals(Config.FROM_EMAIL, message.getFrom());
        assertEquals(user.getEmail(), Objects.requireNonNull(message.getTo())[0]);
        assertEquals("Item Price Update", message.getSubject());
        assertEquals(text, message.getText());
    }
}