package com.example.restservice.inrostructure.net.avitoclient;

import com.example.restservice.inrostructure.net.NetworkWrapper;
import com.example.restservice.inrostructure.utils.Dumper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;

@Component
public class AvitoClient {
    private final NetworkWrapper networkWrapper;
    public static final char[] DIGITS_ARRAY = "0123456789".toCharArray();

    @Autowired
    public AvitoClient(NetworkWrapper networkWrapper) {
        this.networkWrapper = networkWrapper;
    }

    public int getActualPrice(String itemId, String avitoMobileApiKey) throws Exception {
        String avitoItemUrl = getAvitoItemUrl(itemId, avitoMobileApiKey);

        HttpResponse<String> response = networkWrapper.doGet(avitoItemUrl);

//        if (response.headers().firstValue(""))
        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        if (jsonObject.has("error")) {
            handleError(jsonObject);
        }

        if (!jsonObject.has("price")) {
            throw new AvitoApiChangedException("Avito changed items m.avito.ru/api");
        }

        final JsonObject price = jsonObject.get("price").getAsJsonObject();
        if (!price.has("value")) {
            throw new AvitoApiChangedException("Avito changed items m.avito.ru/api");
        }

        String actualPrice = price.get("value").getAsString();

        int x = parsePriceString(actualPrice);
        Dumper.dump(x, System.out);
        return x;
    }

    private void handleError(JsonObject jsonObject) throws AvitoBaseException {
        final JsonObject error = jsonObject.get("error").getAsJsonObject();
        final int code = error.get("code").getAsInt();
        final String message = error.get("message").getAsString();

        switch (code) {
            case AvitoResponseCode.INVALID_ITEM_ID:
                throw new InvalidItemIdException400(message);
            case  AvitoResponseCode.UNKNOWN_KEY:
                throw new UnknownKeyException403(message);
            case  AvitoResponseCode.ITEM_NOT_FOUND:
                throw new ItemNotFoundException404(message);
            default:
                throw new AvitoApiChangedException("Unsupported response code");
        }
    }

    public String getAvitoItemUrl(final String itemId, String avitoMobileApiKey) {
        return "https://m.avito.ru/api/16/items/" + itemId + "?key=" + avitoMobileApiKey;
    }

    private int parsePriceString(String actualPrice) {
        StringBuilder result = new StringBuilder();
        char[] chars = actualPrice.toCharArray();

        for (int i = 0; i < actualPrice.length(); ++i) {
            char aChar = chars[i];
            if (isDigit(aChar)) {
                result.append(aChar);
            }
        }

        return Integer.parseInt(result.toString());
    }

    private boolean isDigit(char aChar) {
        for (char digit : DIGITS_ARRAY) {
            if (aChar == digit) {
                return true;
            }
        }
        return false;
    }

}
