package com.example.restservice.app.service;

import com.example.restservice.app.events.event.OnItemPriceUpdateEvent;
import com.example.restservice.app.model.Item;
import com.example.restservice.app.model.ItemDto;
import com.example.restservice.app.model.Subscription;
import com.example.restservice.app.model.User;
import com.example.restservice.app.repository.ItemRepository;
import com.example.restservice.app.repository.SubscriptionRepository;
import com.example.restservice.inrostructure.config.Config;
import com.example.restservice.inrostructure.net.avitoclient.AvitoBaseException;
import com.example.restservice.inrostructure.net.avitoclient.AvitoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class PriceUpdateCheckerServiceImpl implements PriceUpdateCheckerService {

    private static final Logger log = LoggerFactory.getLogger("logger");

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final AvitoClient avitoClient;
    private final SubscriptionRepository subscriptionRepository;
    private final ItemRepository itemRepository;
    private final ApplicationEventPublisher eventPublisher;
    private int pageSize = 100;

    @Autowired
    public PriceUpdateCheckerServiceImpl(AvitoClient avitoClient,
                                         SubscriptionRepository subscriptionRepository,
                                         ItemRepository itemRepository,
                                         ApplicationEventPublisher eventPublisher) {
        this.avitoClient = avitoClient;
        this.subscriptionRepository = subscriptionRepository;
        this.itemRepository = itemRepository;
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedRate = 1000 * 1000)
    @Override
    public void checkPrice() {
        long l = (subscriptionRepository.getCountOfAllNeededToCheckItems().longValue() / pageSize);
        if (l < 1) {
            l = 1;
        }
        for (int page = 0; page < l; page++) {
            checkPriceItemOnPage(pageSize, page);
        }
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    private void checkPriceItemOnPage(int size, int page) {
        List<Item> activeAndVerifiedSubscription = subscriptionRepository.getNeededCheckByPage(PageRequest.of(page, size));
        List<ItemDto> updatedItemDto = new ArrayList<>();
        List<Item> updatedItem = new ArrayList<>();

        AtomicBoolean isUpdated = new AtomicBoolean(false);
        activeAndVerifiedSubscription.forEach(item -> {
            try {
                final Optional<ItemDto> itemDtoOptional = tryPriceUpdate(item, avitoClient);
                if (itemDtoOptional.isPresent()) {
                    final ItemDto itemDto = itemDtoOptional.get();
                    updatedItemDto.add(itemDto);
                    updatedItem.add(itemDto.getItem());
                    isUpdated.set(true);
                }
            } catch (AvitoBaseException exception) {
                log.warn(exception.getMessage());
            }
        });

        if (isUpdated.get()) {
            itemRepository.saveAll(updatedItem);
            notifySubscriberAboutPriceUpdate(updatedItemDto);
        }
    }

    private Optional<ItemDto> tryPriceUpdate(Item item, AvitoClient avitoClient) throws AvitoBaseException {
        final int actualPrice = avitoClient.getActualPrice(item.getId(), Config.AVITO_MOBILE_API_KEY);
        final int oldPrice = item.getPrice();

        if (oldPrice != actualPrice) {
            item.setPrice(actualPrice);
            final ItemDto itemDto = new ItemDto(item, oldPrice);

            log.info("{} {}", new Date(), getFormat(itemDto.getItem(), oldPrice, actualPrice));
            return Optional.of(itemDto);
        }
        return Optional.empty();
    }

    private void notifySubscriberAboutPriceUpdate(List<ItemDto> updatedItemDto) {
        updatedItemDto.forEach(itemDto -> {
            String itemId = itemDto.getItem().getId();
            List<User> subscribers = getItemSubscribers(itemId);

            subscribers.forEach(subscriber -> {
                eventPublisher.publishEvent(new OnItemPriceUpdateEvent(new Subscription(itemDto.getItem(), subscriber), itemDto.getOldPrice()));
                log.info("{}{}", dateFormat.format(new Date()), getFormat(itemDto.getItem(), itemDto.getItem().getPrice(), itemDto.getOldPrice()));
                log.info("The time is now {}", dateFormat.format(new Date()));
            });
        });
    }

    private List<User> getItemSubscribers(String itemId) {
        List<Object[]> subscriberObjects = subscriptionRepository.getSubscribers(itemId);
        List<User> subscribers = new ArrayList<>();
        subscriberObjects.forEach(objects -> {
            User user = new User((BigInteger) objects[0], String.valueOf(objects[1]), (boolean) objects[2]);
            subscribers.add(user);
        });
        return subscribers;
    }

    private String getFormat(Item item, int actualPrice, int currentPrice) {
        return "The time is now {}. Price update of the item " + item + " updated. Old price " + currentPrice + ", new - " + actualPrice + ".";
    }
}
