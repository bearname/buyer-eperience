package com.example.restservice.inrostructure.net;

import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class Java11HttpClientNetworkWrapperTest {
    @Test
    void test() {
        final Java11HttpClientNetworkWrapper java11HttpClientNetworkWrapper = new Java11HttpClientNetworkWrapper();
        final HttpResponse<String> stringHttpResponse = java11HttpClientNetworkWrapper.doGet("https://google.com");
        assertNotNull(stringHttpResponse);
    }
}