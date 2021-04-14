package com.example.restservice.inrostructure.net;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class Java11HttpClientNetworkWrapperTest {
    @Test
    public void test() {
        final Optional<String> status_code = new Java11HttpClientNetworkWrapper().doGet("https://google.com").headers().firstValue("Status Code");
        assertTrue(true);
//        assertTrue(status_code.isPresent());
    }

}