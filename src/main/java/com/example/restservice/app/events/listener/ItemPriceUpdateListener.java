package com.example.restservice.app.events.listener;

import com.example.restservice.app.events.event.OnItemPriceUpdateEvent;
import com.example.restservice.app.model.Item;
import com.example.restservice.app.model.Subscription;
import com.example.restservice.app.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ItemPriceUpdateListener implements ApplicationListener<OnItemPriceUpdateEvent> {

    private final MailService mailerService;
    private final Logger logger = LoggerFactory.getLogger("logger");

    public ItemPriceUpdateListener(MailService mailerService) {
        this.mailerService = mailerService;
    }

    @Override
    public void onApplicationEvent(OnItemPriceUpdateEvent event) {
        logger.info("New on item price update event listener");

        Subscription subscription = event.getSubscription();
        int oldPrice = event.getOldPrice();
        Item item = subscription.getItem();
        String message = "Item " + item.getUrl() + " updated. Old price " + oldPrice + ", new price " + item.getPrice();
        mailerService.send(event, message + "\r\n");
    }
}
