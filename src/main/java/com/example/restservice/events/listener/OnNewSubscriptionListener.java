package com.example.restservice.events.listener;

import com.example.restservice.events.event.OnNewItemSubscriptionEvent;
import com.example.restservice.model.Subscription;
import com.example.restservice.model.User;
import com.example.restservice.service.MailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OnNewSubscriptionListener implements ApplicationListener<OnNewItemSubscriptionEvent> {

    //    private static final Logger log = LoggerFactory.getLogger(MailerService.class);
    private final MailerService mailerService;

    @Autowired
    public OnNewSubscriptionListener(MailerService mailerService) {
        this.mailerService = mailerService;
    }

    @Override
    public void onApplicationEvent(OnNewItemSubscriptionEvent event) {
        this.confirmSubmission(event);
    }

    private void confirmSubmission(OnNewItemSubscriptionEvent event) {
        String message = "Please verify your submission";
        Subscription subscription = event.getSubscription();
        String confirmationUrl = getConfirmationUrl(subscription, event.getDomain());
        mailerService.send(event, message + "\r\n" + confirmationUrl);
        System.out.println("confirmation url: " + confirmationUrl);
    }

    public static String getConfirmationUrl(final Subscription subscription, final String domain) {
        final User user = subscription.getUser();
        return "http://" + domain + "/api/v1/items/" + subscription.getItem().getId() + "/" + user.getId() + "/confirm?verificationCode=" + subscription.getVerificationCode();
    }
}
