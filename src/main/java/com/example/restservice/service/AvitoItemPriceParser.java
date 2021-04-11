package com.example.restservice.service;

import com.example.restservice.config.Config;
import com.example.restservice.net.NetworkWrapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;

@Component
public class AvitoItemPriceParser {
    private final NetworkWrapper networkWrapper;

    @Autowired
    public AvitoItemPriceParser(NetworkWrapper networkWrapper) {
        this.networkWrapper = networkWrapper;
    }

    public int getActualPrice(String itemId) {
        String avitoItemUrl = getAvitoItemUrl(itemId);

        HttpResponse<String> response = networkWrapper.doGet(avitoItemUrl);
        System.out.println("==============================");
        System.out.println("==============================");
        System.out.println("========ACTUAL=PRICE==========");
        System.out.println("==============================");
        System.out.println("==============================");
        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        String actualPrice = jsonObject.get("price").getAsJsonObject().get("value").getAsString();
        StringBuilder result = new StringBuilder();
        char[] chars = actualPrice.toCharArray();
        for (int i = 0; i < actualPrice.length(); ++i) {
            char aChar = chars[i];
            char[] digits = "0123456789".toCharArray();
            for (char digit : digits) {
                if (aChar == digit) {
                    result.append(aChar);
                    break;
                }
            }
        }

        int x = Integer.parseInt(result.toString());
        System.out.println(x);
        System.out.println("==============================");
        System.out.println("==============================");
        System.out.println("==============================");
        return x;
    }

    private String getAvitoItemUrl(final String itemId) {
        return "https://m.avito.ru/api/16/items/" + itemId + "?key=" + Config.AVITO_MOBILE_API_KEY;
    }
}
