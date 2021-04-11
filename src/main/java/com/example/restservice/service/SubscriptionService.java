package com.example.restservice.service;

import com.example.restservice.events.event.OnNewItemSubscriptionEvent;
import com.example.restservice.events.listener.OnNewSubscriptionListener;
import com.example.restservice.model.Item;
import com.example.restservice.model.Subscription;
import com.example.restservice.model.User;
import com.example.restservice.repository.ItemRepository;
import com.example.restservice.repository.SubscriptionRepository;
import com.example.restservice.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class SubscriptionService {
    private final ApplicationEventPublisher eventPublisher;

    private UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final AvitoItemPriceParser parserAvitoItemPriceService;

    public SubscriptionService(ApplicationEventPublisher eventPublisher, UserRepository userRepository, ItemRepository itemRepository, SubscriptionRepository subscriptionRepository, AvitoItemPriceParser parserAvitoItemPriceService) {
        this.eventPublisher = eventPublisher;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.parserAvitoItemPriceService = parserAvitoItemPriceService;
    }

    public String subscribe(final String email, final String itemUrl, final String host) {
        String itemId = getItemId(itemUrl);
        Subscription checkItem = subscriptionRepository.findByItemIdAndUserEmail(itemId, email);
        if (isDeactivatedItem(checkItem)) {
            resubscribe(checkItem);
            return "your resubscribe";
        } else {
            return subscribeNewItem(email, itemUrl, host, itemId);
        }
    }

    public boolean unsubscribe(String itemId, long userId) throws Exception {
        BigInteger userId1 = BigInteger.valueOf(userId);
        Subscription subscription = subscriptionRepository.findByItemIdAndUserId(itemId, userId1);

        if (subscription == null) {
            throw new Exception("Unknown subscription");
        }

        subscription.setActive(false);
        Subscription save = subscriptionRepository.save(subscription);

        eventPublisher.publishEvent(new OnNewItemSubscriptionEvent(save, "localhost"));
        return true;
    }

    public boolean confirmSubscription(String itemId, String userId, String verificationCode) throws Exception {
        boolean successVerification = false;
        Subscription subscription = subscriptionRepository.findByItemIdAndUserId(itemId, BigInteger.valueOf(Long.parseLong(userId)));
        if (subscription.getVerificationCode().equals(verificationCode)) {
            if (subscription.isVerified()) {
                throw new Exception("subscription already verified");
            }

            subscription.setVerified(true);
            subscriptionRepository.save(subscription);
            successVerification = true;
        }

        return successVerification;
    }

    private boolean isDeactivatedItem(Subscription byItemIdAndUserEmail) {
        return byItemIdAndUserEmail != null && byItemIdAndUserEmail.isVerified() && !byItemIdAndUserEmail.isActive();
    }

    private void resubscribe(Subscription byItemIdAndUserEmail) {
        byItemIdAndUserEmail.setActive(true);
        subscriptionRepository.save(byItemIdAndUserEmail);
    }

    private String subscribeNewItem(String email, String itemUrl, String host, String itemId) {
        final int actualPrice = parserAvitoItemPriceService.getActualPrice(itemId);

        try {
            Item item = new Item(itemId, actualPrice, itemUrl);
            if (!itemRepository.existsById(itemId)) {
                item = itemRepository.save(item);
            } else {
                item = itemRepository.getById(itemId);
            }

            User user = new User(email);
            if (!userRepository.existsByEmail(email)) {
                user = userRepository.save(user);
            } else {
                user = userRepository.getByEmail(email);
            }

            Optional<String> s = checkSubscription(host, item, user);
            if (s.isPresent()) {
                return s.get();
            }

            Subscription savedSubscription = subscriptionRepository.save( new Subscription(item, user));

            eventPublisher.publishEvent(new OnNewItemSubscriptionEvent(savedSubscription, host));

            return email + " " + itemUrl + " " + itemId;
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return "failed";
    }

    private Optional<String> checkSubscription(String host, Item item, User user) {
        Optional<String> result = Optional.empty();
        Subscription subscription1 = subscriptionRepository.findByItemIdAndUserId(item.getId(), user.getId());
        if (subscription1 != null) {
            String message = "Subscription already exists.";
            if (!subscription1.isVerified()) {
                message += " PLease confirm your subscription " + OnNewSubscriptionListener.getConfirmationUrl(subscription1, host);
            }

            result = Optional.of(message);
        }
        return result;
    }

    private String getItemId(String itemUrl) {
        return itemUrl.substring(itemUrl.lastIndexOf("_") + 1);
    }

    public Page<Subscription> getUserSubscription(Long userId, Integer page, Integer limit) throws Exception {
        Optional<User> byId = this.userRepository.findById(BigInteger.valueOf(userId));
        if (byId.isEmpty()) {
            throw new Exception("Unknown user with id " + userId);
        }
        System.out.println(byId);
        return this.subscriptionRepository.following(BigInteger.valueOf(userId), PageRequest.of(page, limit));
    }
}
