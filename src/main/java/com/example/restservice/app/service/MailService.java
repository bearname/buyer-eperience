package com.example.restservice.app.service;

import com.example.restservice.app.events.BaseApplicationEvent;

public interface MailService {
    void send(BaseApplicationEvent event, String message);

}
