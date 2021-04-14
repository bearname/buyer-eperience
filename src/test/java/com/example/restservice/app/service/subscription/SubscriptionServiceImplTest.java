package com.example.restservice.app.service.subscription;

import com.example.restservice.app.events.MockEventPublisher;
import com.example.restservice.app.events.event.OnNewItemSubscriptionEvent;
import com.example.restservice.app.events.listener.OnNewSubscriptionListener;
import com.example.restservice.app.model.Item;
import com.example.restservice.app.model.Subscription;
import com.example.restservice.app.model.User;
import com.example.restservice.app.repository.ItemRepository;
import com.example.restservice.app.repository.MockUserRepositoryNotExistUser;
import com.example.restservice.app.repository.SubscriptionRepository;
import com.example.restservice.app.repository.UserRepository;
import com.example.restservice.app.service.SubscriptionService;
import com.example.restservice.app.service.SubscriptionServiceImpl;
import com.example.restservice.inrostructure.config.Config;
import com.example.restservice.inrostructure.net.avitoclient.AvitoBaseException;
import com.example.restservice.inrostructure.net.avitoclient.AvitoClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static com.example.restservice.app.repository.MockSubscriptionRepository.ITEMS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class SubscriptionServiceImplTest {

    @Test
    public void failedGetNewPrice() {
        final AvitoBaseException aviatorApiException = new AvitoBaseException("Avito Api excpetion");
        try {
            final String subscriptionUrl = "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286";
            final String userEmail = "test@mail.com";
            final String itemId = "2112597286";

            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            when(avitoClient.getActualPrice(itemId, Config.AVITO_MOBILE_API_KEY)).thenThrow(aviatorApiException);

            ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
            UserRepository userRepository = Mockito.mock(UserRepository.class);
            SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
            ApplicationEventPublisher applicationEventPublisher = Mockito.mock(ApplicationEventPublisher.class);

            SubscriptionService subscriptionService = new SubscriptionServiceImpl(applicationEventPublisher, userRepository, itemRepository, subscriptionRepository, avitoClient);
            final String host = "localhost";
            final String actual = subscriptionService.subscribe(userEmail, subscriptionUrl, host);
        } catch (AvitoBaseException exception) {
            assertEquals(aviatorApiException.getMessage(), exception.getMessage());
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }

    @Test
    public void failedGetNewPrice1() {
        try {
            final String subscriptionUrl = "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286";
            final String userEmail = "test@mail.com";
            final String itemId = "2112597286";

            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            when(avitoClient.getActualPrice(itemId, Config.AVITO_MOBILE_API_KEY)).thenThrow(new Exception("failed"));

            ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
            UserRepository userRepository = Mockito.mock(UserRepository.class);
            SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
            ApplicationEventPublisher applicationEventPublisher = Mockito.mock(ApplicationEventPublisher.class);

            SubscriptionService subscriptionService = new SubscriptionServiceImpl(applicationEventPublisher, userRepository, itemRepository, subscriptionRepository, avitoClient);
            final String host = "localhost";
            final String actual = subscriptionService.subscribe(userEmail, subscriptionUrl, host);
            assertEquals("failed", actual);
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }

    @Test
    public void subscriptionAlreadyExistsAndNotNeedConfirmSubscription() {
        try {
            final String subscriptionUrl = "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286";
            final String userEmail = "test@mail.com";
            final String itemId = "2112597286";
            final int itemPrice = 2500;
            ApplicationEventPublisher applicationEventPublisher = Mockito.mock(ApplicationEventPublisher.class);

            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            when(avitoClient.getActualPrice(itemId, Config.AVITO_MOBILE_API_KEY)).thenReturn(itemPrice);

            ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
            when(itemRepository.existsById(itemId)).thenReturn(false);
            final Item item = new Item(itemId, itemPrice, subscriptionUrl);
            when(itemRepository.save(item)).thenReturn(item);

            final User user = new User(userEmail);
            UserRepository userRepository = new MockUserRepositoryNotExistUser();

            final Subscription subscription = new Subscription(item, user);
            subscription.setVerified(true);
            SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
            when(subscriptionRepository.findByItemIdAndUserEmail(itemId, userEmail)).thenReturn(null);
            when(subscriptionRepository.findByItemIdAndUserId(item.getId(), user.getId())).thenReturn(subscription);

            SubscriptionService subscriptionService = new SubscriptionServiceImpl(applicationEventPublisher, userRepository, itemRepository, subscriptionRepository, avitoClient);
            final String host = "localhost";
            final String actual = subscriptionService.subscribe("test@mail.com", subscriptionUrl, host);
            final String expected = "Subscription already exists.";
            System.out.println("expected: " + expected);
            System.out.println("actual: " + actual);
            assertEquals(expected, actual);
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }

    @Test
    public void subscriptionAlreadyExistsAndNeedConfirmSubscription() {
        try {
            final String itemUrl = "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286";
            final String userEmail = "test@mail.com";
            final String itemId = "2112597286";
            final int itemPrice = 2500;
            ApplicationEventPublisher applicationEventPublisher = Mockito.mock(ApplicationEventPublisher.class);

            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            when(avitoClient.getActualPrice(itemId, Config.AVITO_MOBILE_API_KEY)).thenReturn(itemPrice);

            ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
            when(itemRepository.existsById(itemId)).thenReturn(false);
            final Item item = new Item(itemId, itemPrice, itemUrl);
            when(itemRepository.save(item)).thenReturn(item);

            final User user = new User(userEmail);
            UserRepository userRepository = new MockUserRepositoryNotExistUser();

            final Subscription subscription = new Subscription(item, user);
            SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
            when(subscriptionRepository.findByItemIdAndUserEmail(itemId, userEmail)).thenReturn(null);
            when(subscriptionRepository.findByItemIdAndUserId(item.getId(), user.getId())).thenReturn(subscription);

            final SubscriptionService subscriptionService = new SubscriptionServiceImpl(applicationEventPublisher, userRepository, itemRepository, subscriptionRepository, avitoClient);
            final String host = "localhost";
            final String actual = subscriptionService.subscribe("test@mail.com", itemUrl, host);
            final String expected = "Subscription already exists. PLease confirm your subscription " + OnNewSubscriptionListener.getConfirmationUrl(subscription, host);
            System.out.println("expected: " + expected);
            System.out.println("actual: " + actual);
            assertEquals(expected, actual);
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }

    @Test
    public void itsNewSubscription() {
        try {
            final String itemUrl = "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286";
            final String userEmail = "test@mail.com";
            final String itemId = "2112597286";
            final int itemPrice = 2500;
            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            when(avitoClient.getActualPrice(itemId, Config.AVITO_MOBILE_API_KEY)).thenReturn(itemPrice);

            ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
            when(itemRepository.existsById(itemId)).thenReturn(true);
            final Item item = new Item(itemId, itemPrice, itemUrl);
            when(itemRepository.getById(itemId)).thenReturn(item);

            final User user = new User(userEmail);
            UserRepository userRepository = new MockUserRepositoryNotExistUser();

            final Subscription subscription = new Subscription(item, user);
            subscription.setId(1L);

            SubscriptionRepository subscriptionRepository = new MockSubscriptionRepository();

            final String host = "localhost";
            ApplicationEventPublisher eventPublisher = new MockEventPublisher();
            SubscriptionService subscriptionService = new SubscriptionServiceImpl(eventPublisher, userRepository, itemRepository, subscriptionRepository, avitoClient);
            final String actual = subscriptionService.subscribe("test@mail.com", itemUrl, host);
            final String expected = userEmail + " " + itemUrl + " " + itemId;
            System.out.println("expected: " + expected);
            System.out.println("actual: " + actual);
            assertEquals(expected, actual);
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }

    @Test
    public void itsNewSubscription1() {
        try {
            final String itemUrl = "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286";
            final String userEmail = "test@mail.com";
            final String itemId = "2112597286";
            final int itemPrice = 2500;
            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            when(avitoClient.getActualPrice(itemId, Config.AVITO_MOBILE_API_KEY)).thenReturn(itemPrice);

            ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
            when(itemRepository.existsById(itemId)).thenReturn(true);
            final Item item = new Item(itemId, itemPrice, itemUrl);
            when(itemRepository.getById(itemId)).thenReturn(item);

            final User user = new User(userEmail);
            UserRepository userRepository = new MockUserRepositoryExistUser();

            final Subscription subscription = new Subscription(item, user);
            subscription.setId(1L);
            subscription.setActive(false);
            subscription.setVerified(true);

            SubscriptionRepository subscriptionRepository = new MockSubscriptionRepository();

            final String host = "localhost";
            ApplicationEventPublisher eventPublisher = new MockEventPublisher();
            SubscriptionService subscriptionService = new SubscriptionServiceImpl(eventPublisher, userRepository, itemRepository, subscriptionRepository, avitoClient);
            final String actual = subscriptionService.subscribe("test@mail.com", itemUrl, host);
            final String expected = userEmail + " " + itemUrl + " " + itemId;
            System.out.println("expected: " + expected);
            System.out.println("actual: " + actual);
            assertEquals(expected, actual);
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }

    @Test
    public void itsNewSubscription2() {
        try {
            final String itemUrl = "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286";
            final String userEmail = "test@mail.com";
            final String itemId = "2112597286";
            final int itemPrice = 2500;
            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            when(avitoClient.getActualPrice(itemId, Config.AVITO_MOBILE_API_KEY)).thenReturn(itemPrice);

            ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
            when(itemRepository.existsById(itemId)).thenReturn(true);
            final Item item = new Item(itemId, itemPrice, itemUrl);
            when(itemRepository.getById(itemId)).thenReturn(item);

            final User user = new User(userEmail);
            UserRepository userRepository = new MockUserRepositoryNotExistUser();

            final Subscription subscription = new Subscription(item, user);
            subscription.setId(1L);
            subscription.setActive(true);
            subscription.setVerified(true);

            SubscriptionRepository subscriptionRepository = new MockSubscriptionRepository();

            final String host = "localhost";
            ApplicationEventPublisher eventPublisher = new MockEventPublisher();
            SubscriptionService subscriptionService = new SubscriptionServiceImpl(eventPublisher, userRepository, itemRepository, subscriptionRepository, avitoClient);
            final String actual = subscriptionService.subscribe("test@mail.com", itemUrl, host);
            final String expected = userEmail + " " + itemUrl + " " + itemId;
            System.out.println("expected: " + expected);
            System.out.println("actual: " + actual);
            assertEquals(expected, actual);
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }

    @Test
    public void resubscribe() {
        try {
            final String subscriptionUrl = "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286";
            final String userEmail = "test@mail.com";
            final String itemId = "2112597286";
            final int itemPrice = 2500;
            ApplicationEventPublisher applicationEventPublisher = Mockito.mock(ApplicationEventPublisher.class);

            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            ItemRepository itemRepository = Mockito.mock(ItemRepository.class);


            UserRepository userRepository = new MockUserRepositoryNotExistUser();

            final Item item = new Item(itemId, itemPrice, subscriptionUrl);
            final User user = new User(userEmail);
            final Subscription subscription = new Subscription(item, user);
            subscription.setActive(false);
            subscription.setVerified(true);
            SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
            when(subscriptionRepository.findByItemIdAndUserEmail(itemId, userEmail)).thenReturn(subscription);
            when(subscriptionRepository.save(subscription)).thenReturn(subscription);
            SubscriptionService subscriptionService = new SubscriptionServiceImpl(applicationEventPublisher, userRepository, itemRepository, subscriptionRepository, avitoClient);

            final String host = "localhost";
            final String actual = subscriptionService.subscribe("test@mail.com", subscriptionUrl, host);
            final String expected = "your resubscribe";
            System.out.println("expected: " + expected);
            System.out.println("actual: " + actual);
            assertEquals(expected, actual);
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }

    @Test
    public void unsubscribeUnknownUser() {
        try {
            final String itemId = "2112597286";

            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
            when(itemRepository.existsById(itemId)).thenReturn(false);

            UserRepository userRepository = new MockUserRepositoryNotExistUser();

            SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
            ApplicationEventPublisher applicationEventPublisher = Mockito.mock(ApplicationEventPublisher.class);
            SubscriptionService subscriptionService = new SubscriptionServiceImpl(applicationEventPublisher, userRepository, itemRepository, subscriptionRepository, avitoClient);
            final boolean unsubscribe = subscriptionService.unsubscribe(itemId, 1L);
            assertFalse(unsubscribe);
        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void unsubscribe() {
        try {
            final String itemUrl = "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286";
            final String userEmail = "test@mail.com";
            final String itemId = "2112597286";
            final int itemPrice = 2500;

            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
            UserRepository userRepository = Mockito.mock(UserRepository.class);

            final Item item = new Item(itemId, itemPrice, itemUrl);
            final User user = new User(userEmail);
            final Subscription subscription = new Subscription(item, user);

            subscription.setVerified(false);
            SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
            when(subscriptionRepository.findByItemIdAndUserId(item.getId(), BigInteger.valueOf(1L))).thenReturn(subscription);
            subscription.setActive(true);

            when(subscriptionRepository.save(subscription)).thenReturn(subscription);

            ApplicationEventPublisher applicationEventPublisher = Mockito.mock(ApplicationEventPublisher.class);
            doNothing().when(applicationEventPublisher).publishEvent(new OnNewItemSubscriptionEvent(subscription, "localhost"));

            final SubscriptionService subscriptionService = new SubscriptionServiceImpl(applicationEventPublisher, userRepository, itemRepository, subscriptionRepository, avitoClient);
            final boolean unsubscribe = subscriptionService.unsubscribe(itemId, 1L);
            assertTrue(unsubscribe);
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }

    @Test
    public void confirmSubscriptionEmailUnknownSubscription() {
        try {
            final String itemUrl = "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286";
            final String userEmail = "test@mail.com";
            final String itemId = "2112597286";
            final int itemPrice = 2500;

            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
            UserRepository userRepository = Mockito.mock(UserRepository.class);

            final Item item = new Item(itemId, itemPrice, itemUrl);
            final User user = new User(userEmail);
            final Subscription subscription = new Subscription(item, user);

            subscription.setVerified(false);
            final String verificationCode = UUID.randomUUID().toString();
            subscription.setVerificationCode(verificationCode);

            SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
            when(subscriptionRepository.findByItemIdAndUserId(item.getId(), BigInteger.valueOf(1L))).thenReturn(null);

            ApplicationEventPublisher applicationEventPublisher = Mockito.mock(ApplicationEventPublisher.class);

            final SubscriptionService subscriptionService = new SubscriptionServiceImpl(applicationEventPublisher, userRepository, itemRepository, subscriptionRepository, avitoClient);
            subscriptionService.confirmSubscription(itemId, verificationCode, BigInteger.valueOf(1L));
        } catch (Exception exception) {
            assertEquals("Unknown subscription", exception.getMessage());
        }
    }

    @Test
    public void invalidVerificationCode() {
        try {
            final String itemUrl = "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286";
            final String userEmail = "test@mail.com";
            final String itemId = "2112597286";
            final int itemPrice = 2500;

            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
            UserRepository userRepository = Mockito.mock(UserRepository.class);

            final Item item = new Item(itemId, itemPrice, itemUrl);
            final User user = new User(userEmail);
            final Subscription subscription = new Subscription(item, user);

            subscription.setVerified(false);
            final String verificationCode = UUID.randomUUID().toString();
            subscription.setVerificationCode(verificationCode);

            SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
            when(subscriptionRepository.findByItemIdAndUserId(item.getId(), BigInteger.valueOf(1L))).thenReturn(subscription);

            ApplicationEventPublisher applicationEventPublisher = Mockito.mock(ApplicationEventPublisher.class);

            final SubscriptionService subscriptionService = new SubscriptionServiceImpl(applicationEventPublisher, userRepository, itemRepository, subscriptionRepository, avitoClient);
            final boolean unsubscribe = subscriptionService.confirmSubscription(itemId, UUID.randomUUID().toString(), BigInteger.valueOf(1L));
            assertFalse(unsubscribe);
        } catch (Exception exception) {
            fail();
            exception.printStackTrace();
        }
    }

    @Test
    public void subscriptionAlreadyVerified() {
        try {
            final String itemUrl = "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286";
            final String userEmail = "test@mail.com";
            final String itemId = "2112597286";
            final int itemPrice = 2500;

            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
            UserRepository userRepository = Mockito.mock(UserRepository.class);

            final Item item = new Item(itemId, itemPrice, itemUrl);
            final User user = new User(1L, userEmail);
            final Subscription subscription = new Subscription(item, user);
            subscription.setVerified(true);
            final String verificationCode = UUID.randomUUID().toString();
            subscription.setVerificationCode(verificationCode);

            SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
            when(subscriptionRepository.findByItemIdAndUserId(item.getId(), BigInteger.valueOf(1L))).thenReturn(subscription);
            ApplicationEventPublisher applicationEventPublisher = Mockito.mock(ApplicationEventPublisher.class);

            final SubscriptionService subscriptionService = new SubscriptionServiceImpl(applicationEventPublisher, userRepository, itemRepository, subscriptionRepository, avitoClient);
            final boolean unsubscribe = subscriptionService.confirmSubscription(itemId, verificationCode, BigInteger.valueOf(1L));
        } catch (Exception exception) {
            assertEquals("Subscription already verified", exception.getMessage());
        }
    }

    @Test
    public void successConfirmation() {
        try {
            final String itemUrl = "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286";
            final String userEmail = "test@mail.com";
            final String itemId = "2112597286";
            final int itemPrice = 2500;

            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
            UserRepository userRepository = Mockito.mock(UserRepository.class);

            final Item item = new Item(itemId, itemPrice, itemUrl);
            final User user = new User(userEmail);
            final Subscription subscription = new Subscription(item, user);
            subscription.setVerified(false);
            final String verificationCode = UUID.randomUUID().toString();
            subscription.setVerificationCode(verificationCode);

            SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
            when(subscriptionRepository.findByItemIdAndUserId(item.getId(), BigInteger.valueOf(1L))).thenReturn(subscription);
            when(subscriptionRepository.save(subscription)).thenReturn(subscription);

            ApplicationEventPublisher applicationEventPublisher = Mockito.mock(ApplicationEventPublisher.class);

            final SubscriptionService subscriptionService = new SubscriptionServiceImpl(applicationEventPublisher, userRepository, itemRepository, subscriptionRepository, avitoClient);
            final boolean unsubscribe = subscriptionService.confirmSubscription(itemId, subscription.getVerificationCode(), BigInteger.valueOf(1L));
            assertTrue(unsubscribe);
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }


    @Test
    public void getUserSubscriptionsByUnknownUserId() {
        try {
            final String itemId = "2112597286";

            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            ItemRepository itemRepository = Mockito.mock(ItemRepository.class);

            UserRepository userRepository = Mockito.mock(UserRepository.class);
            final long userId = 1L;
            final BigInteger id = BigInteger.valueOf(userId);
            when(userRepository.findById(id)).thenReturn(Optional.empty());

            SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
            ApplicationEventPublisher applicationEventPublisher = Mockito.mock(ApplicationEventPublisher.class);
            SubscriptionService subscriptionService = new SubscriptionServiceImpl(applicationEventPublisher, userRepository, itemRepository, subscriptionRepository, avitoClient);
            final Page<Subscription> userSubscriptions = subscriptionService.getUserSubscriptions(1L, 0, 10);
            fail();
        } catch (Exception exception) {
            assertEquals("Unknown user with id 1", exception.getMessage());
        }
    }
    @Test
    public void getUserSubscriptions() {
        try {
            final String itemId = "2112597286";

            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            ItemRepository itemRepository = Mockito.mock(ItemRepository.class);

            UserRepository userRepository = Mockito.mock(UserRepository.class);
            final long userId = 1L;
            final User user = new User(1L, "test@mail.com");
            final BigInteger id = BigInteger.valueOf(userId);
            when(userRepository.findById(id)).thenReturn( Optional.of(user));

            SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
            final int page = 0;
            final int limit = 10;
            final ArrayList<Subscription> subscriptions1 = new ArrayList<>();
            final Item item = new Item("2112597286", 50000, "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286");
            final Item item1 = new Item("1", 50000, "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_1");
            final Item item2 = new Item("100", 50000, "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_100");
            subscriptions1.add(new Subscription(item, user));
            subscriptions1.add(new Subscription(item1, user));
            subscriptions1.add(new Subscription(item2, user));
            final Page<Subscription> subscriptions = new PageImpl<>(subscriptions1);

            when(subscriptionRepository.following(id, PageRequest.of(page, limit))).thenReturn(subscriptions);
            ApplicationEventPublisher applicationEventPublisher = Mockito.mock(ApplicationEventPublisher.class);
            SubscriptionService subscriptionService = new SubscriptionServiceImpl(applicationEventPublisher, userRepository, itemRepository, subscriptionRepository, avitoClient);
            final Page<Subscription> userSubscriptions = subscriptionService.getUserSubscriptions(1L, page, limit);
            assertTrue(true);
        } catch (Exception exception) {
            fail();
        }
    }

}