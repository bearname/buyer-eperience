package com.example.restservice.inrostructure.net.avitoclient;

import com.example.restservice.inrostructure.config.Config;
import com.example.restservice.inrostructure.net.NetworkWrapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class AvitoClientTest {

    private NetworkWrapper networkWrapper;

    public String getAvitoItemUrl(final String itemId, String avitoMobileApiKey) {
        return "https://m.avito.ru/api/16/items/" + itemId + "?key=" + avitoMobileApiKey;
    }

    @Test
    public void invalidKey() {

        try {
            NetworkWrapper dataServiceMock = Mockito.mock(NetworkWrapper.class);
            when(dataServiceMock.doGet(getAvitoItemUrl("2135576825", "asdasd"))).thenReturn(MockResponse.ITEM_UNKNOWN_KEY_403);
            final AvitoClient avitoClient = new AvitoClient(dataServiceMock);
            final int item = avitoClient.getActualPrice("2135576825", "asdasd");
            fail();
        } catch (AvitoBaseException exception) {
            if (exception instanceof UnknownKeyException403) {
                assertTrue(true);
            } else {
                fail();
            }
        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void invalidItemId() {

        try {
            NetworkWrapper dataServiceMock = Mockito.mock(NetworkWrapper.class);
            when(dataServiceMock.doGet(getAvitoItemUrl("items", Config.AVITO_MOBILE_API_KEY))).thenReturn(MockResponse.INVALID_ITEM_ID_400);
            final AvitoClient avitoClient = new AvitoClient(dataServiceMock);
            final int item = avitoClient.getActualPrice("items", Config.AVITO_MOBILE_API_KEY);
            fail();
        } catch (AvitoBaseException exception) {
            if (exception instanceof InvalidItemIdException400) {
                assertTrue(true);
            } else {
                fail();
            }
        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void itemNotFound() {

        try {
            NetworkWrapper dataServiceMock = Mockito.mock(NetworkWrapper.class);
            when(dataServiceMock.doGet(getAvitoItemUrl("2135576825", Config.AVITO_MOBILE_API_KEY))).thenReturn(MockResponse.ITEM_NOT_FOUND_404);
            final AvitoClient avitoClient = new AvitoClient(dataServiceMock);
            final int item = avitoClient.getActualPrice("2135576825", Config.AVITO_MOBILE_API_KEY);
            fail();
        } catch (AvitoBaseException exception) {
            if (exception instanceof ItemNotFoundException404) {
                assertTrue(true);
            } else {
                fail();
            }
        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void avitoChangeMobileApiResponse() {

        try {
            NetworkWrapper dataServiceMock = Mockito.mock(NetworkWrapper.class);
            when(dataServiceMock.doGet(getAvitoItemUrl("2135576825", Config.AVITO_MOBILE_API_KEY))).thenReturn(MockResponse.ITEM_NOT_FOUND_404);
            final AvitoClient avitoClient = new AvitoClient(dataServiceMock);
            final int item = avitoClient.getActualPrice("2135576825", Config.AVITO_MOBILE_API_KEY);
            fail();
        } catch (AvitoBaseException exception) {
            if (exception instanceof ItemNotFoundException404) {
                assertTrue(true);
            } else {
                fail();
            }
        } catch (Exception exception) {
            fail();
        }
    }


    @Test
    public void avitoResponseNotSetPriceField() {
        try {
            NetworkWrapper dataServiceMock = Mockito.mock(NetworkWrapper.class);
            when(dataServiceMock.doGet(getAvitoItemUrl("2135576825", Config.AVITO_MOBILE_API_KEY))).thenReturn(MockResponse.NO_HAS_PRICE_200);
            final AvitoClient avitoClient = new AvitoClient(dataServiceMock);
            final int item = avitoClient.getActualPrice("2135576825", Config.AVITO_MOBILE_API_KEY);
            fail();
        } catch (AvitoBaseException exception) {
            if (exception instanceof AvitoApiChangedException) {
                assertTrue(true);
            } else {
                fail();
            }
        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void avitoResponseJsonNoHaveValueFieldOnPriceField() {
        try {
            NetworkWrapper dataServiceMock = Mockito.mock(NetworkWrapper.class);
            when(dataServiceMock.doGet(getAvitoItemUrl("2135576825", Config.AVITO_MOBILE_API_KEY))).thenReturn(MockResponse.NO_HAS_PRICE_VALUE_200);
            final AvitoClient avitoClient = new AvitoClient(dataServiceMock);
            final int item = avitoClient.getActualPrice("2135576825", Config.AVITO_MOBILE_API_KEY);
            fail();
        } catch (AvitoBaseException exception) {
            if (exception instanceof AvitoApiChangedException) {
                assertTrue(true);
            } else {
                fail();
            }
        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    public void notSupportedResponseCode() {
        try {
            NetworkWrapper dataServiceMock = Mockito.mock(NetworkWrapper.class);
            when(dataServiceMock.doGet(getAvitoItemUrl("2135576825", Config.AVITO_MOBILE_API_KEY))).thenReturn(MockResponse.NOT_SUPPORTED_RESPONSE_CODE);
            final AvitoClient avitoClient = new AvitoClient(dataServiceMock);
            final int item = avitoClient.getActualPrice("2135576825", Config.AVITO_MOBILE_API_KEY);
            fail();
        } catch (Exception exception) {
            if (exception instanceof AvitoApiChangedException) {
                assertEquals(exception.getMessage(), "Unsupported response code");
            } else {
                fail();
            }
        }
    }

    @Test
    public void valid() {
        try {
            NetworkWrapper dataServiceMock = Mockito.mock(NetworkWrapper.class);
            when(dataServiceMock.doGet(getAvitoItemUrl("2135576825", Config.AVITO_MOBILE_API_KEY))).thenReturn(MockResponse.VALID_ITEM_FOUND_200);
            final AvitoClient avitoClient = new AvitoClient(dataServiceMock);
            final int item = avitoClient.getActualPrice("2135576825", Config.AVITO_MOBILE_API_KEY);
            assertEquals(item, 22500);
        } catch (Exception exception) {
            fail();
        }
    }
}