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

@Component
public class PriceUpdateCheckerServiceImpl implements PriceUpdateCheckerService {

//    private static final Logger log = LoggerFactory.getLogger(PriceUpdaterService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final AvitoClient avitoClient;
    private final SubscriptionRepository subscriptionRepository;
    private final ItemRepository itemRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public PriceUpdateCheckerServiceImpl(AvitoClient parserAvitoItemPriceService, SubscriptionRepository subscriptionRepository, ItemRepository itemRepository, ApplicationEventPublisher eventPublisher) {
        this.avitoClient = parserAvitoItemPriceService;
        this.subscriptionRepository = subscriptionRepository;
        this.itemRepository = itemRepository;
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedRate = 1000 * 1000)
    @Override
    public void checkPrice() {
        int size = 100;
        for (int page = 0; page < subscriptionRepository.getNeededCheckCount().longValue() / size; page++) {
            checkPriceItemOnPage(size, page);
        }
    }

    private void checkPriceItemOnPage(int size, int page) {
        List<Item> activeAndVerifiedSubscription = subscriptionRepository.getNeededCheck(PageRequest.of(page, size));
        List<ItemDto> updatedItemDto = new ArrayList<>();
        List<Item> updatedItem = new ArrayList<>();

        activeAndVerifiedSubscription.forEach(item -> {
            try {
                final Optional<ItemDto> itemDtoOptional = tryPriceUpdate(item, avitoClient);
                if (itemDtoOptional.isPresent()) {
                    final ItemDto itemDto = itemDtoOptional.get();
                    updatedItemDto.add(itemDto);
                    updatedItem.add(itemDto.getItem());
                }
            } catch (AvitoBaseException exception) {
                exception.printStackTrace();
            }
        });

        itemRepository.saveAll(updatedItem);
        notifySubscriberAboutPriceUpdate(updatedItemDto);
    }

    private Optional<ItemDto> tryPriceUpdate(Item item, AvitoClient avitoClient) throws AvitoBaseException {
        final int actualPrice = avitoClient.getActualPrice(item.getId(), Config.AVITO_MOBILE_API_KEY);
        final int oldPrice = item.getPrice();
        System.out.println(oldPrice + " " + actualPrice);

        if (oldPrice != actualPrice) {
            System.out.println(oldPrice + " " + actualPrice);
            item.setPrice(actualPrice);
            final ItemDto itemDto = new ItemDto(item, oldPrice);

            return Optional.of(itemDto);
//                log.info(getFormat(subscription, actualPrice, currentPrice), dateFormat.format(new Date())  );
        }
        return Optional.empty();
    }

    private void notifySubscriberAboutPriceUpdate(List<ItemDto> updatedItemDto) {
        updatedItemDto.forEach(itemDto -> {
            String id = itemDto.getItem().getId();
            List<User> subscribers = getSubscribers(id);

            subscribers.forEach(subscriber -> {
                eventPublisher.publishEvent(new OnItemPriceUpdateEvent(new Subscription(itemDto.getItem(), subscriber), itemDto.getOldPrice()));
                System.out.println(dateFormat.format(new Date()) + getFormat(itemDto.getItem(), itemDto.getItem().getPrice(), itemDto.getOldPrice()));
                System.out.println("The time is now {}" + dateFormat.format(new Date()));
            });
        });
    }

    private List<User> getSubscribers(String id) {
        List<Object[]> subscriberObjects = subscriptionRepository.getSubscribers(id);
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
