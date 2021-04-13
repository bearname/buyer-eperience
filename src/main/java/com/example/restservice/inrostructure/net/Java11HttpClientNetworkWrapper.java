package com.example.restservice.inrostructure.net;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class Java11HttpClientNetworkWrapper implements NetworkWrapper {

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
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
