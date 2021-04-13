package com.example.restservice.inrostructure.net;

import java.net.http.HttpResponse;

public interface NetworkWrapper {
    HttpResponse<String> doGet(String url);
}
