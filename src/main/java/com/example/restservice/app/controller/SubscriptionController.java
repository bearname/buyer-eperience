package com.example.restservice.app.controller;

import com.example.restservice.app.model.Subscription;
import com.example.restservice.app.service.PatternValidator;
import com.example.restservice.app.service.SubscriptionService;
import com.example.restservice.app.service.ValidateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/items")
public class SubscriptionController {

    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_PAGE_LIMIT = 10;
    private final PatternValidator patternValidatorService;

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(PatternValidator emailValidator, SubscriptionService subscriptionService) {
        this.patternValidatorService = emailValidator;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping(path = "/{userId}")
    @ResponseBody
    public ResponseEntity<Object> userSubscription(@PathVariable("userId") Long userId,
                                                   @RequestParam(value = "page", required = false) Integer page,
                                                   @RequestParam(value = "limit", required = false) Integer limit,
                                                   @RequestHeader(name = "Host", required = false) final String host,
                                                   HttpServletRequest request) {
        System.out.println(request);
        System.out.println("======================");
        System.out.println("======================");
        System.out.println("======================");
        System.out.println(request.getLocalName());
        System.out.println(request.getContextPath());
        System.out.println(request.getLocalAddr());
        System.out.println(request.getRemoteUser());
        System.out.println(request.getRemoteUser());
        System.out.println("Host " + host);
        System.out.println("======================");
        System.out.println("======================");
        System.out.println("======================");

        if (page == null || page < 0) {
            page = DEFAULT_PAGE;
        }
        if (limit == null || limit < 0) {
            limit = DEFAULT_PAGE_LIMIT;
        }

        Map<String, Object> result = new HashMap<>();
        try {
            Page<Subscription> userSubscriptions = this.subscriptionService.getUserSubscriptions(userId, page, limit);

            result.put("subscriptions", userSubscriptions);
            result.put("userId", userId);
            result.put("success", true);
            return ResponseEntity
                    .ok()
                    .body(result);
        } catch (Exception exception) {
            result.put("success", false);
            result.put("message", "Unknown user with id " + userId);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/subscribe")
    @ResponseBody
    public ResponseEntity<Object> subscribe(@RequestHeader(name = "Host", required = false) final String host,
                                            @RequestBody Map<String, String> payload) {
        final String email = payload.get("email");
        String message;
        Map<String, Object> result = new HashMap<>();
        if (!patternValidatorService.isValid(email, ValidateType.EMAIL)) {
            message = "Invalid email";
            if (email != null) {
                message += ": "  + email;
            }

            result.put("message", message);
            result.put("success", false);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(result);
        }

        final String avitoItemUrl = payload.get("url");
        if (!patternValidatorService.isValid(avitoItemUrl, ValidateType.URL)) {
            message = "Invalid url";
            if (avitoItemUrl != null) {
                message += ": "  + avitoItemUrl;
            }
            result.put("message", message);
            result.put("success", false);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(result);
        }

        message = subscriptionService.subscribe(email, avitoItemUrl, host);

        result.put("success", true);
        result.put("message", message);

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PostMapping("/{itemId}/{userId}/unsubscribe")
    @ResponseBody
    public ResponseEntity<Object> unsubscribe(@PathVariable(name = "userId") String userId,
                                           @PathVariable(name = "itemId") String itemId) {
        Map<String, Object> result = new HashMap<>();
        try {
            final boolean success = subscriptionService.unsubscribe(itemId, Long.parseLong(userId));

            if (success) {
                result.put("success", true);
                result.put("message", "Success cancel subscription");
            } else {
                result.put("success", false);
                result.put("message", "Failed cancel subscription");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            result.put("success", false);
            result.put("message", "Failed cancel subscription");
        }

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/{itemId}/{userId}/confirm")
    @ResponseBody
    public ResponseEntity<Object> confirm(@PathVariable(name = "userId") String userId,
                                       @PathVariable(name = "itemId") String itemId,
                                       @RequestParam("verificationCode") String verificationCode) {
        Map<String, Object> result = new HashMap<>();
        try {
            System.out.println(itemId + " " + userId + " " + verificationCode);

            final BigInteger userId1 = BigInteger.valueOf(Long.parseLong(userId));
            final boolean successVerification = subscriptionService.confirmSubscription(itemId, verificationCode, userId1);
            result.put("success", successVerification);
            result.put("message", (successVerification ? "Success" : "Failed") + " verified your email");
        } catch (Exception exception) {
            exception.printStackTrace();
            result.put("success", false);
            result.put("message", "Failed verified your email");
        }

        return ResponseEntity
                .ok()
                .body(result);
    }
}