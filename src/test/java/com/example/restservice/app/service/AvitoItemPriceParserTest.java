package com.example.restservice.app.service;

import com.example.restservice.inrostructure.net.avitoclient.AvitoClient;
import com.example.restservice.inrostructure.net.Java11HttpClientNetworkWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class AvitoItemPriceParserTest {

    private AvitoClient avitoItemPriceParser;

    @BeforeEach
    public void initForEach() {
        final Java11HttpClientNetworkWrapper networkWrapper = mock(Java11HttpClientNetworkWrapper.class);
        this.avitoItemPriceParser = new AvitoClient(networkWrapper);
    }
    @Test
    public void tests() {
//        when();
//        this.avitoItemPriceParser.getActualPrice("2112597286");
//        assertEquals(2, calculator.add(1, 1));
    }

}