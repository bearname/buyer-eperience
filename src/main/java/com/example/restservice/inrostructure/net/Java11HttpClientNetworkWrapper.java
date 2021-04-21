package com.example.restservice.inrostructure.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class Java11HttpClientNetworkWrapper implements NetworkWrapper {

    private static final Logger log = LoggerFactory.getLogger("logger");

    @Override
    public HttpResponse<String> doGet(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
            return HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException | URISyntaxException exception) {
            log.error("InterruptedException: ", exception);
            Thread.currentThread().interrupt();
            return null;
        }
    }
}
