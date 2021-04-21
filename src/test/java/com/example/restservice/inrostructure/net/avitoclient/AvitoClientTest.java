package com.example.restservice.inrostructure.net.avitoclient;

import com.example.restservice.inrostructure.config.Config;
import com.example.restservice.inrostructure.net.NetworkWrapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AvitoClientTest {

    public String getAvitoItemUrl(final String itemId, String avitoMobileApiKey) {
        return "https://m.avito.ru/api/16/items/" + itemId + "?key=" + avitoMobileApiKey;
    }

    @Test
    void invalidKey() {
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
    void invalidItemId() {
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
    void invalidItemResponseNotHasCodeField() {
        try {
            NetworkWrapper dataServiceMock = Mockito.mock(NetworkWrapper.class);
            when(dataServiceMock.doGet(getAvitoItemUrl("items", Config.AVITO_MOBILE_API_KEY))).thenReturn(MockResponse.WITHOUT_CODE_FIELD_ERROR_RESPONSE);
            final AvitoClient avitoClient = new AvitoClient(dataServiceMock);
            final int item = avitoClient.getActualPrice("items", Config.AVITO_MOBILE_API_KEY);
            fail();
        } catch (AvitoBaseException exception) {
            assertEquals("Error not have 'code' field", exception.getMessage());
        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    void invalidItemResponseNotHasMessageField() {
        try {
            NetworkWrapper dataServiceMock = Mockito.mock(NetworkWrapper.class);
            when(dataServiceMock.doGet(getAvitoItemUrl("items", Config.AVITO_MOBILE_API_KEY))).thenReturn(MockResponse.WITHOUT_MESSAGE_FIELD_ERROR_RESPONSE);
            final AvitoClient avitoClient = new AvitoClient(dataServiceMock);
            final int item = avitoClient.getActualPrice("items", Config.AVITO_MOBILE_API_KEY);
            fail();
        } catch (AvitoBaseException exception) {
            assertEquals("Error not have 'message' field", exception.getMessage());
        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    void itemNotFound() {
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
    void avitoChangeMobileApiResponse() {

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
    void avitoResponseNotSetPriceField() {
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
    void avitoResponseJsonNoHaveValueFieldOnPriceField() {
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
    void notSupportedResponseCode() {
        try {
            NetworkWrapper dataServiceMock = Mockito.mock(NetworkWrapper.class);
            when(dataServiceMock.doGet(getAvitoItemUrl("2135576825", Config.AVITO_MOBILE_API_KEY))).thenReturn(MockResponse.NOT_SUPPORTED_RESPONSE_CODE);
            final AvitoClient avitoClient = new AvitoClient(dataServiceMock);
            final int item = avitoClient.getActualPrice("2135576825", Config.AVITO_MOBILE_API_KEY);
            fail();
        } catch (Exception exception) {
            if (exception instanceof AvitoApiChangedException) {
                assertEquals("Unsupported response code", exception.getMessage());
            } else {
                fail();
            }
        }
    }

    @Test
    void valid() {
        try {
            NetworkWrapper dataServiceMock = Mockito.mock(NetworkWrapper.class);
            when(dataServiceMock.doGet(getAvitoItemUrl("2135576825", Config.AVITO_MOBILE_API_KEY))).thenReturn(MockResponse.VALID_ITEM_FOUND_200);
            final AvitoClient avitoClient = new AvitoClient(dataServiceMock);
            final int item = avitoClient.getActualPrice("2135576825", Config.AVITO_MOBILE_API_KEY);
            assertEquals(22500, item);
        } catch (Exception exception) {
            fail();
        }
    }
}