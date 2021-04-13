package com.example.restservice.app.controller;

import com.example.restservice.app.model.Subscription;
import com.example.restservice.app.service.PatternValidator;
import com.example.restservice.app.service.SubscriptionService;
import com.example.restservice.app.service.ValidateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/items")
public class SubscriptionController {

    private final PatternValidator patternValidatorService;

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(PatternValidator emailValidator, SubscriptionService subscriptionService) {
        this.patternValidatorService = emailValidator;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping(path = "/{userId}")
    @ResponseBody
    public Map<String, Object> userSubscription(@PathVariable("userId") Long userId,
                                                @RequestParam(value = "page", required = false) Integer page,
                                                @RequestParam(value = "limit", required = false) Integer limit,
                                                @RequestHeader(name = "Host", required = false) final String host,
                                                HttpServletRequest request) {
        System.out.println(request);
        System.out.println("======================");
        System.out.println("======================");
        System.out.println("======================");
        System.out.println( request.getLocalName());
        System.out.println( request.getContextPath());
        System.out.println( request.getLocalAddr());
        System.out.println( request.getRemoteUser());
        System.out.println( request.getRemoteUser());
        System.out.println("Host " + host);
        System.out.println("======================");
        System.out.println("======================");
        System.out.println("======================");
        if (page == null && limit == null) {
            page = 0;
            limit = 10;
        }
        Map<String, Object> result = new HashMap<>();
        try {
            Page<Subscription> userSubscriptions = this.subscriptionService.getUserSubscriptions(userId, page, limit);
            result.put("subscriptions", userSubscriptions);
            result.put("userId", userId);
        } catch (Exception exception) {
            exception.printStackTrace();
            result.put("status", "Unknown user " + userId);
        }

        return result;
    }

    @PostMapping("/subscribe")
    @ResponseBody
    public Map<String, Object> subscribe(@RequestHeader(name = "Host", required = false) final String host,
                                         @RequestBody Map<String, String> payload) {
        final String email = payload.get("email");
        String s;
        if (!patternValidatorService.isValid(email, ValidateType.EMAIL)) {
            s = "invalid email:" + email;
        } else {
            final String avitoItemUrl = payload.get("url");
            if (!patternValidatorService.isValid(avitoItemUrl, ValidateType.URL)) {
                s = "invalid url: " + avitoItemUrl;
            } else {
                s  = subscriptionService.subscribe(email, avitoItemUrl, host);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("message", s);

        return result;
    }

    @PostMapping("/{itemId}/{userId}/unsubscribe")
    @ResponseBody
    public Map<String, Object> unsubscribe(@PathVariable(name = "userId") String userId,
                                           @PathVariable(name = "itemId") String itemId) {
        Map<String, Object> result = new HashMap<>();
        try {
            final boolean success = subscriptionService.unsubscribe(itemId, Long.parseLong(userId));

            if (success) {
                result.put("status", "success");
                result.put("message", "success cancel subscription");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            result.put("status", "failed");
            result.put("message", "failed cancel subscription");
        }

        return result;
    }

    @GetMapping("/{itemId}/{userId}/confirm")
    @ResponseBody
    public Map<String, Object> confirm(@PathVariable(name = "userId") String userId,
                                       @PathVariable(name = "itemId") String itemId,
                                       @RequestParam("verificationCode") String verificationCode) {
        Map<String, Object> result = new HashMap<>();
        try {
            System.out.println(itemId + " " + userId + " " + verificationCode);

            final BigInteger userId1 = BigInteger.valueOf(Long.parseLong(userId));
            final boolean successVerification = subscriptionService.confirmSubscription(itemId, verificationCode, userId1);
            result.put("status", (successVerification ? "success" : "failed"));
            result.put("message", (successVerification ? "success" : "failed") + " verified your email");
        } catch (Exception exception) {
            exception.printStackTrace();
            result.put("status", "failed");
            result.put("message", "failed verified your email");
        }

        return result;
    }
}