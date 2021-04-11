package com.example.restservice.service;

import com.example.restservice.events.event.OnItemPriceUpdateEvent;
import com.example.restservice.model.Item;
import com.example.restservice.model.ItemDto;
import com.example.restservice.model.Subscription;
import com.example.restservice.model.User;
import com.example.restservice.repository.ItemRepository;
import com.example.restservice.repository.SubscriptionRepository;
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

@Component
public class PriceUpdateCheckerService {

//    private static final Logger log = LoggerFactory.getLogger(PriceUpdaterService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final AvitoItemPriceParser parserAvitoItemPriceService;
    private final SubscriptionRepository subscriptionRepository;
    private final ItemRepository itemRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public PriceUpdateCheckerService(AvitoItemPriceParser parserAvitoItemPriceService, SubscriptionRepository subscriptionRepository, ItemRepository itemRepository, ApplicationEventPublisher eventPublisher) {
        this.parserAvitoItemPriceService = parserAvitoItemPriceService;
        this.subscriptionRepository = subscriptionRepository;
        this.itemRepository = itemRepository;
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedRate = 1000 * 1000)
    public void checkPrice() {
        int size = 100;
        for (int page = 0; page < subscriptionRepository.getNeededCheckCount().longValue() / size; page++) {
            List<Item> activeAndVerifiedSubscription = subscriptionRepository.getNeededCheck(PageRequest.of(page, size));
            System.out.println("=============================");
            System.out.println("=============================");
            System.out.println("activeAndVerifiedSubscription");

            activeAndVerifiedSubscription.forEach(System.out::println);
            System.out.println("=============================");
            System.out.println("=============================");
            System.out.println("=============================");
            List<ItemDto> updatedItemDto = new ArrayList<>();
            List<Item> updatedItem = new ArrayList<>();

            activeAndVerifiedSubscription.forEach(item -> {
                final int actualPrice = parserAvitoItemPriceService.getActualPrice(item.getId());
                final int oldPrice = item.getPrice();
                System.out.println(oldPrice + " " + actualPrice);

                if (oldPrice != actualPrice) {
                    System.out.println(oldPrice + " " + actualPrice);
                    item.setPrice(actualPrice);
                    updatedItemDto.add(new ItemDto(item, oldPrice));
                    updatedItem.add(item);
//                log.info(getFormat(subscription, actualPrice, currentPrice), dateFormat.format(new Date())  );
                }
            });
            itemRepository.saveAll(updatedItem);
            notifySubscriber(updatedItemDto);
        }
    }

    private void notifySubscriber(List<ItemDto> updatedItemDto) {
        updatedItemDto.forEach(itemDto -> {
            String id = itemDto.getItem().getId();
            List<Object[]> subscriberObjects = subscriptionRepository.getSubscribers(id);
            List<User> subscribers = new ArrayList<>();
            subscriberObjects.forEach(objects -> {
                User e = new User((BigInteger) objects[0], String.valueOf(objects[1]), (boolean) objects[2]);
                subscribers.add(e);
            });
            System.out.println("=============================");
            System.out.println("=============================");
            System.out.println("subscriptions");
            subscribers.forEach(System.out::println);
            System.out.println("=============================");
            System.out.println("=============================");
            System.out.println("=============================");
            subscribers.forEach(subscriber -> {
                eventPublisher.publishEvent(new OnItemPriceUpdateEvent(new Subscription(itemDto.getItem(), subscriber), itemDto.getOldPrice()));
                System.out.println(dateFormat.format(new Date()) + getFormat(itemDto.getItem(), itemDto.getItem().getPrice(), itemDto.getOldPrice()));
                System.out.println("The time is now {}" + dateFormat.format(new Date()));
            });
        });
    }

    private String getFormat(Item item, int actualPrice, int currentPrice) {
        return "The time is now {}. Price update of the item " + item + " updated. Old price " + currentPrice + ", new - " + actualPrice + ".";
    }
}
