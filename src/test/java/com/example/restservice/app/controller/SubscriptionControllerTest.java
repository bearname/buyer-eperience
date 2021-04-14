package com.example.restservice.app.controller;

import com.example.restservice.app.model.Item;
import com.example.restservice.app.model.Subscription;
import com.example.restservice.app.model.User;
import com.example.restservice.app.repository.ItemRepository;
import com.example.restservice.app.repository.SubscriptionRepository;
import com.example.restservice.app.repository.UserRepository;
import com.example.restservice.app.service.SubscriptionService;
import com.example.restservice.inrostructure.net.avitoclient.AvitoClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
//@Sql(value = {"create-entity-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(value = {"delete-entity-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class SubscriptionControllerTest {

    @MockBean
    private SubscriptionService subscriptionService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SubscriptionController subscriptionController;

    public static final PageImpl<Subscription> EMPTY = new PageImpl<>(new ArrayList<>());

    @Test
    public void noParamGreetingShouldReturnNotFound() throws Exception {
        when(subscriptionService.getUserSubscriptions(1L, 0, 10)).thenThrow(new Exception("Unknown user"));

        this.mockMvc.perform(get("/api/v1/items/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void hasInvalidPageParameterValue() throws Exception {
        when(subscriptionService.getUserSubscriptions(1L, 0, 10)).thenReturn(EMPTY);

        this.mockMvc.perform(get("/api/v1/items/1?page=ASDf"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void hasInvalidPageParameterValue1() throws Exception {
        final long userId = 1L;
        when(subscriptionService.getUserSubscriptions(userId, 0, 10)).thenReturn(EMPTY);

        this.mockMvc.perform(get("/api/v1/items/1?page=123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.userId", is(1)))
                .andReturn();
    }

    @Test
    public void hasInvalidLimitParameterValue() throws Exception {
        when(subscriptionService.getUserSubscriptions(1L, 0, 10)).thenReturn(EMPTY);

        this.mockMvc.perform(get("/api/v1/items/1?limit=ASDf"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void pageParameterLessThanZero() throws Exception {
        when(subscriptionService.getUserSubscriptions(1L, 0, 10)).thenReturn(EMPTY);

        final int userId = 1;
        this.mockMvc.perform(get("/api/v1/items/" + userId + "?limit=-1"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.userId", is(userId)))
                .andExpect(jsonPath("$.subscriptions.content", hasSize(0)))
                .andReturn();
    }

    @Test
    public void notHaveSubscription() throws Exception {
        when(subscriptionService.getUserSubscriptions(1L, 0, 10)).thenReturn(EMPTY);

        final int userId = 1;
        this.mockMvc.perform(get("/api/v1/items/" + userId + "?limit=-1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.userId", is(userId)))
                .andExpect(jsonPath("$.subscriptions.content", hasSize(0)))
                .andReturn();
    }

    @Test
    public void haveSubscriptionMoreThanZero() throws Exception {
        final User user = new User(1L, "test@mail.com");
        final ArrayList<Subscription> subscriptions1 = new ArrayList<>();
        final Item item = new Item("2112597286", 50000, "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286");
        final Item item1 = new Item("1", 50000, "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_1");
        final Item item2 = new Item("100", 50000, "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_100");
        subscriptions1.add(new Subscription(item, user));
        subscriptions1.add(new Subscription(item1, user));

        final Page<Subscription> subscriptions = new PageImpl<>(subscriptions1);

        when(subscriptionService.getUserSubscriptions(1L, 0, 2)).thenReturn(subscriptions);

        final int value = 1;
        final int userId = 1;
        this.mockMvc.perform(get("/api/v1/items/" + userId + "?limit=2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.userId", is(value)))
                .andExpect(jsonPath("$.subscriptions.content", hasSize(2)))
                .andReturn();
    }

    @Test
    public void emptyBody() throws Exception {

        mockMvc.perform(post("/api/v1/items/subscribe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void emptyJson() throws Exception {
        mockMvc.perform(post("/api/v1/items/subscribe")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Invalid email")))
                .andReturn();
    }

    @Test
    public void notHaveRequiredParamter() throws Exception {
        mockMvc.perform(post("/api/v1/items/subscribe")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\": \"application123\"}"))

                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Invalid email")))
                .andReturn();

    }

    @Test
    public void invalidEmail() throws Exception {
        mockMvc.perform(post("/api/v1/items/subscribe")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"notemail\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Invalid email: notemail")))
                .andReturn();
    }

    @Test
    public void haveOnlyValidEmailParameter() throws Exception {
        mockMvc.perform(post("/api/v1/items/subscribe")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"ya.mikushoff@gmail.com\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Invalid url")))
                .andReturn();
    }

    @Test
    public void validEmailAndInvalidUrl() throws Exception {
        mockMvc.perform(post("/api/v1/items/subscribe")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"ya.mikushoff@gmail.com\", \"url\": \"https\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Invalid url: https")))
                .andReturn();
    }

    @Test
    public void onlyValidUrl() throws Exception {
        mockMvc.perform(post("/api/v1/items/subscribe")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\": \"https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286\"}"))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    public void valid() throws Exception {
        final User user = new User(1L, "test@mail.com");
        final ArrayList<Subscription> subscriptions1 = new ArrayList<>();
        final Item item = new Item("2112597286", 50000, "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_2112597286");
        final Item item1 = new Item("1", 50000, "https://www.avito.ru/kazan/tovary_dlya_kompyutera/asus_gaming_vg248qg_1");
        subscriptions1.add(new Subscription(item, user));
        subscriptions1.add(new Subscription(item1, user));

        final Page<Subscription> subscriptions = new PageImpl<>(subscriptions1);
        when(subscriptionService.subscribe("ya.mikushoff@gmail.com",
                "https://www.avito.ru/kazan/tovary_dlya_kompyutera/novyy_34_monitor_lg_34gl750_2560x1080144gts219_2116447881",
                "localhost"))
                .thenReturn("succes");

        mockMvc.perform(post("/api/v1/items/subscribe")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"ya.mikushoff@gmail.com\", \"url\": \"https://www.avito.ru/kazan/tovary_dlya_kompyutera/novyy_34_monitor_lg_34gl750_2560x1080144gts219_2116447881\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andReturn();

    }

    @Test
    public void failedUnsubscribe() throws Exception {
        when(subscriptionService.unsubscribe("1", 1)).thenReturn(false);

        mockMvc.perform(post("/api/v1/items/1/1/unsubscribe"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Failed cancel subscription")))
                .andReturn();
    }

    @Test
    public void successUnsubscribe() throws Exception {
        when(subscriptionService.unsubscribe("1", 1)).thenReturn(true);

        mockMvc.perform(post("/api/v1/items/1/1/unsubscribe"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Success cancel subscription")))
                .andReturn();
    }

    @Test
    public void confirmWithoutVerificationCode() throws Exception {
        final String verificationCode = UUID.randomUUID().toString();
        when(subscriptionService.confirmSubscription("1", verificationCode, BigInteger.valueOf(1))).thenReturn(false);

        mockMvc.perform(get("/api/v1/items/1/1/confirm"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void invalidVerificationCode() throws Exception {
        final String verificationCode = UUID.randomUUID().toString();
        when(subscriptionService.confirmSubscription("1", verificationCode, BigInteger.valueOf(1))).thenReturn(false);

        mockMvc.perform(get("/api/v1/items/1/1/confirm?verificationCode=" + verificationCode))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Failed verified your email")))
                .andReturn();
    }

    class MockService implements SubscriptionService {

        @Override
        public String subscribe(String email, String itemUrl, String host) {
            return null;
        }

        @Override
        public boolean unsubscribe(String itemId, long userId) throws Exception {
            return false;
        }

        @Override
        public boolean confirmSubscription(String itemId, String verificationCode, BigInteger userId) throws Exception {
            return false;
        }

        @Override
        public Page<Subscription> getUserSubscriptions(Long userId, Integer page, Integer limit) throws Exception {
            return null;
        }
    }

    @Test
    public void unknownSubscription() throws Exception {
        final String verificationCode = UUID.randomUUID().toString();
        when(subscriptionService.confirmSubscription("1", verificationCode, BigInteger.valueOf(1))).thenThrow(new Exception("Unknown exception"));

        mockMvc.perform(get("/api/v1/items/1/1/confirm?verificationCode=" + verificationCode))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Failed verified your email")))
                .andReturn();
    }

    @Test
    public void validConfirmation() throws Exception {
        final String verificationCode = UUID.randomUUID().toString();
        when(subscriptionService.confirmSubscription("1", verificationCode, BigInteger.valueOf(1))).thenReturn(true);

        mockMvc.perform(get("/api/v1/items/1/1/confirm?verificationCode=" + verificationCode))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Success verified your email")))
                .andReturn();
    }


}