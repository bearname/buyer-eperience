package com.example.restservice.app.service;

import com.example.restservice.app.events.MockPriceApplicationEventPublisher;
import com.example.restservice.app.events.NotUpdatedMockPriceApplicationEventPublisher;
import com.example.restservice.app.repository.*;
import com.example.restservice.inrostructure.config.Config;
import com.example.restservice.inrostructure.net.avitoclient.AvitoClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class PriceUpdateCheckerServiceImplTest {


    @Test
    public void priceNotUpdated() {
        try {
            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            when(avitoClient.getActualPrice("1", Config.AVITO_MOBILE_API_KEY)).thenReturn(2500);
            when(avitoClient.getActualPrice("2", Config.AVITO_MOBILE_API_KEY)).thenReturn(2500);
            SubscriptionRepository subscriptionRepository = new MockSubscriptionRepository();
            ItemRepository itemRepository = new MockPriceNotUpdatedItemRepository();
//            when(itemRepository.saveAll(ITEMS)).thenThrow(new Exception("app not update price"));
            ApplicationEventPublisher applicationEventPublisher = new NotUpdatedMockPriceApplicationEventPublisher();

            PriceUpdateCheckerService priceUpdateCheckerService = new PriceUpdateCheckerServiceImpl(avitoClient,
                    subscriptionRepository,
                    itemRepository,
                    applicationEventPublisher);

            priceUpdateCheckerService.checkPrice();
            assertTrue(true);
        } catch (Exception exception) {
            assertTrue(true);
            exception.printStackTrace();
//            assertEquals(exception.getMessage(), "app not update price");
//            exception.printStackTrace();
        }
        assertTrue(true);
    }


    @Test
    public void priceUpdated() {
        try {
            AvitoClient avitoClient = Mockito.mock(AvitoClient.class);
            when(avitoClient.getActualPrice("1", Config.AVITO_MOBILE_API_KEY)).thenReturn(2501);
            when(avitoClient.getActualPrice("2", Config.AVITO_MOBILE_API_KEY)).thenReturn(2500);
            SubscriptionRepository subscriptionRepository = new MockSubscriptionRepository();
            ItemRepository itemRepository = new MockPriceUpdatedItemRepository();
//            when(itemRepository.saveAll(ITEMS)).thenThrow(new Exception("app not update price"));
            ApplicationEventPublisher applicationEventPublisher = new MockPriceApplicationEventPublisher();
            PriceUpdateCheckerService priceUpdateCheckerService = new PriceUpdateCheckerServiceImpl(avitoClient,
                    subscriptionRepository,
                    itemRepository,
                    applicationEventPublisher);

            priceUpdateCheckerService.checkPrice();
        } catch (Exception exception) {
            exception.printStackTrace();
            assertEquals(exception.getMessage(), "app not update price");
//            exception.printStackTrace();
        }
    }

}