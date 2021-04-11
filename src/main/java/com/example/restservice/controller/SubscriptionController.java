package com.example.restservice.controller;

import com.example.restservice.model.Subscription;
import com.example.restservice.service.PatternValidatorService;
import com.example.restservice.service.SubscriptionService;
import com.example.restservice.service.ValidateType;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/items")
public class SubscriptionController {

    private final PatternValidatorService patternValidatorService;

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(PatternValidatorService emailValidator, SubscriptionService subscriptionService) {
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
            Page<Subscription> userSubscriptions = this.subscriptionService.getUserSubscription(userId, page, limit);
            result.put("subscriptions", userSubscriptions);
            result.put("userId", userId);
        } catch (Exception exception) {
            exception.printStackTrace();
            result.put("status", "Unknown user " + userId);
        }

        return result;
    }

    @PostMapping("/subscribe")
    public String subscribe(@RequestHeader(name = "Host", required = false) final String host,
                            @RequestBody Map<String, String> payload) {
        final String email = payload.get("email");
        if (!patternValidatorService.isValid(email, ValidateType.EMAIL)) {
            return "invalid email:" + email;
        }

        final String avitoItemUrl = payload.get("url");
        if (!patternValidatorService.isValid(avitoItemUrl, ValidateType.URL)) {
            return "invalid url: " + avitoItemUrl;
        }

        return subscriptionService.subscribe(email, avitoItemUrl, host);
    }

    @PostMapping("/{itemId}/{userId}/unsubscribe")
    public String unsubscribe(@PathVariable(name = "userId") String userId,
                              @PathVariable(name = "itemId") String itemId,
                              @RequestHeader(name = "Host", required = false) final String host) {
        try {
            final boolean success = subscriptionService.unsubscribe(itemId, Long.parseLong(userId));

            if (success) {
                return "success cancel subscription";
            }
        } catch (ConstraintViolationException exception) {
            exception.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return "failed";
    }

    @GetMapping("/{itemId}/{userId}/confirm")
    public String confirm(@PathVariable(name = "userId") String userId,
                          @PathVariable(name = "itemId") String itemId,
                          @RequestParam("verificationCode") String verificationCode) {
        try {
            System.out.println(itemId + " " + userId + " " + verificationCode);

            final boolean successVerification = subscriptionService.confirmSubscription(itemId, userId, verificationCode);
            return (successVerification ? "success" : "failed") + " verified your email";
        } catch (Exception exception) {
            exception.printStackTrace();
            return exception.getMessage();
        }
    }
}