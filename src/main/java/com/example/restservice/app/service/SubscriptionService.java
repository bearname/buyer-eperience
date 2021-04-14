package com.example.restservice.app.service;

import com.example.restservice.app.model.Subscription;
import org.springframework.data.domain.Page;

import java.math.BigInteger;

public interface SubscriptionService {
    String subscribe(final String email, final String itemUrl, final String host);
    boolean unsubscribe(String itemId, long userId) throws Exception;

    boolean confirmSubscription(String itemId, String verificationCode, BigInteger userId) throws Exception ;
    Page<Subscription> getUserSubscriptions(Long userId, Integer page, Integer limit) throws Exception;
}
