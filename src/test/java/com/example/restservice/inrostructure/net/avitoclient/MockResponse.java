package com.example.restservice.inrostructure.net.avitoclient;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;

public class MockResponse {
    public static final BiPredicate<String,String> ACCEPT_ALL = (x, y) -> true;
    public static final HttpResponse<String> NO_HAS_PRICE_200 = new HttpResponse<>() {

        @Override
        public int statusCode() {
            return 200;
        }

        @Override
        public HttpRequest request() {
            return null;
        }

        @Override
        public Optional<HttpResponse<String>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            Map<String, List<String>> headers = new HashMap<>();

            return HttpHeaders.of(headers, ACCEPT_ALL);
        }

        @Override
        public String body() {
            return "{\"id\":2135576825,\"categoryId\":98}";
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return URI.create("https://m.avito.ru/api/16/items/d?key=af0deccbgcgidddjgnvljitntccdduijhdinfgjgfjir");
        }

        @Override
        public HttpClient.Version version() {
            return HttpClient.Version.HTTP_1_1;
        }
    };
    public static final HttpResponse<String> NO_HAS_PRICE_VALUE_200 = new HttpResponse<>() {
        @Override
        public int statusCode() {
            return 200;
        }

        @Override
        public HttpRequest request() {
            return null;
        }

        @Override
        public Optional<HttpResponse<String>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            Map<String, List<String>> headers = new HashMap<>();

            return HttpHeaders.of(headers, ACCEPT_ALL);
        }

        @Override
        public String body() {
            return "{\"id\":2135576825,\"categoryId\":98, \"price\":{\"title\":\"Цена\",\"value_signed\":\"22 500 ₽\",\"metric\":\"₽\"}}";
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return URI.create("https://m.avito.ru/api/16/items/d?key=af0deccbgcgidddjgnvljitntccdduijhdinfgjgfjir");
        }

        @Override
        public HttpClient.Version version() {
            return HttpClient.Version.HTTP_1_1;
        }
    };

    public static final HttpResponse<String> INVALID_ITEM_ID_400 = new HttpResponse<>() {

        @Override
        public int statusCode() {
            return 400;
        }

        @Override
        public HttpRequest request() {
            return null;
        }

        @Override
        public Optional<HttpResponse<String>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            Map<String, List<String>> headers = new HashMap<>();

            return HttpHeaders.of(headers, ACCEPT_ALL);
        }

        @Override
        public String body() {
            return "{\"error\":{\"code\":400,\"message\":\"\"}}";
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return URI.create("https://m.avito.ru/api/16/items/d?key=af0deccbgcgidddjgnvljitntccdduijhdinfgjgfjir");
        }

        @Override
        public HttpClient.Version version() {
            return HttpClient.Version.HTTP_1_1;
        }
    };

    public static final HttpResponse<String> ITEM_NOT_FOUND_404 = new HttpResponse<>() {

        @Override
        public int statusCode() {
            return 404;
        }

        @Override
        public HttpRequest request() {
            return null;
        }

        @Override
        public Optional<HttpResponse<String>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            Map<String, List<String>> headers = new HashMap<>();

            return HttpHeaders.of(headers, ACCEPT_ALL);
        }

        @Override
        public String body() {
            return "{\"error\":{\"code\":404,\"message\":\"Объявление не найдено\"}}";
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return URI.create("https://m.avito.ru/api/16/items/12324?key=af0deccbgcgidddjgnvljitntccdduijhdinfgjgfjir");
        }

        @Override
        public HttpClient.Version version() {
            return HttpClient.Version.HTTP_1_1;
        }
    };

    public static final HttpResponse<String> ITEM_UNKNOWN_KEY_403 = new HttpResponse<>() {

        @Override
        public int statusCode() {
            return 403;
        }

        @Override
        public HttpRequest request() {
            return null;
        }

        @Override
        public Optional<HttpResponse<String>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            Map<String, List<String>> headers = new HashMap<>();

            return HttpHeaders.of(headers, ACCEPT_ALL);
        }

        @Override
        public String body() {
            return "{\"error\":{\"code\":403,\"message\":\"Неизвестный ключ\"}}";
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return URI.create("https://m.avito.ru/api/16/items/2135576825?key=affgjgfjir");
        }

        @Override
        public HttpClient.Version version() {
            return HttpClient.Version.HTTP_1_1;
        }
    };

    public static final HttpResponse<String> NOT_SUPPORTED_RESPONSE_CODE = new HttpResponse<>() {

        @Override
        public int statusCode() {
            return 500;
        }

        @Override
        public HttpRequest request() {
            return null;
        }

        @Override
        public Optional<HttpResponse<String>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            Map<String, List<String>> headers = new HashMap<>();

            return HttpHeaders.of(headers, ACCEPT_ALL);
        }

        @Override
        public String body() {
            return "{\"error\":{\"code\":500,\"message\":\"Неизвестный ключ\"}}";
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return URI.create("https://m.avito.ru/api/16/items/2135576825?key=affgjgfjir");
        }

        @Override
        public HttpClient.Version version() {
            return HttpClient.Version.HTTP_1_1;
        }
    };

    public static final HttpResponse<String> VALID_ITEM_FOUND_200 = new HttpResponse<>() {

        @Override
        public int statusCode() {
            return 200;
        }

        @Override
        public HttpRequest request() {
            return null;
        }

        @Override
        public Optional<HttpResponse<String>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            Map<String, List<String>> headers = new HashMap<>();

            return HttpHeaders.of(headers, ACCEPT_ALL);
        }

        @Override
        public String body() {
            return "{\"id\":2135576825,\"categoryId\":98,\"locationId\":648760,\"sharing\":{\"fb\":\"https://www.avito.ru/yoshkar-ola/noutbuki/asus_k543ub_2135576825?utm_campaign=fb&utm_medium=item_page_mavnew&utm_source=soc_sharing\",\"gp\":\"https://www.avito.ru/yoshkar-ola/noutbuki/asus_k543ub_2135576825?utm_campaign=gp&utm_medium=item_page_mavnew&utm_source=soc_sharing\",\"lj\":\"https://www.avito.ru/yoshkar-ola/noutbuki/asus_k543ub_2135576825?utm_campaign=lj&utm_medium=item_page_mavnew&utm_source=soc_sharing\",\"mm\":\"https://www.avito.ru/yoshkar-ola/noutbuki/asus_k543ub_2135576825?utm_campaign=mm&utm_medium=item_page_mavnew&utm_source=soc_sharing\",\"native\":\"https://www.avito.ru/yoshkar-ola/noutbuki/asus_k543ub_2135576825?utm_campaign=native&utm_medium=item_page_mavnew&utm_source=soc_sharing\",\"ok\":\"https://www.avito.ru/yoshkar-ola/noutbuki/asus_k543ub_2135576825?utm_campaign=ok&utm_medium=item_page_mavnew&utm_source=soc_sharing\",\"tw\":\"https://www.avito.ru/yoshkar-ola/noutbuki/asus_k543ub_2135576825?utm_campaign=tw&utm_medium=item_page_mavnew&utm_source=soc_sharing\",\"vk\":\"https://www.avito.ru/yoshkar-ola/noutbuki/asus_k543ub_2135576825?utm_campaign=vk&utm_medium=item_page_mavnew&utm_source=soc_sharing\",\"url\":\"https://www.avito.ru/yoshkar-ola/noutbuki/asus_k543ub_2135576825\"},\"coords\":{\"lat\":56.631595,\"lng\":47.886178},\"shouldShowMapPreview\":true,\"address\":\"Республика Марий Эл, Йошкар-Ола\",\"geoReferences\":[],\"title\":\"Asus K543UB\",\"userType\":\"private\",\"time\":1617774963,\"description\":\"Oтличный нoутбук, cоcтoяние идeальное. Испoльзовaлся администрaтopoм в мaгaзинe, былa уcтaновлена 1Скa и автокад. Без прoблем потянет игры, тaнки идут xоpoшо. Kуплeн год нaзад за 36 тыcяч рублeй. Пpодам за 22500, нужнa oпpeдeлeннaя cумма наличнocти. Есть дoрoгая cумкa, мoжно договoритьcя c ней. \\nЭкрaн 15,6 дюйма, мaтовый\\nПрофессор Intеl 4417 2,3Ггц(аналог соrе i3) \\nОперативная память 4Гб\\nДве видеокарты, одна встроенная + игровая 2Гб GеFоrсе\\nЖёсткий диск 500Гб\\nНоутбук вернули к заводским настройками как из магазина. Полностью чистый и настроенный.\",\"advertOptions\":[],\"parameters\":{\"flat\":[{\"title\":\"Производитель\",\"description\":\"ASUS\"},{\"title\":\"Состояние\",\"description\":\"Новое\"},{\"title\":\"Категория\",\"description\":\"Ноутбуки\"}],\"groups\":[]},\"images\":[{\"240x180\":\"https://20.img.avito.st/image/1/IVzVj7ayjbWDL2e1m790FDIsjbN1Lo8\",\"140x105\":\"https://20.img.avito.st/image/1/IVzVj7ayjbX7Ll22m790FDIsjbN1Lo8\",\"100x75\":\"https://20.img.avito.st/image/1/IVzVj7ayjbWrLRm2m790FDIsjbN1Lo8\",\"1280x960\":\"https://20.img.avito.st/image/1/IVzVj7ayjbXjOA-4m790FDIsjbN1Lo8\",\"640x480\":\"https://20.img.avito.st/image/1/IVzVj7ayjbXjJk-wm790FDIsjbN1Lo8\",\"432x324\":\"https://20.img.avito.st/image/1/IVzVj7ayjbWDKgeym790FDIsjbN1Lo8\"},{\"1280x960\":\"https://21.img.avito.st/image/1/XFCv9ray8LmZQXK0m_cJGEhV8L8PV_I\",\"640x480\":\"https://21.img.avito.st/image/1/XFCv9ray8LmZXzK8m_cJGEhV8L8PV_I\",\"432x324\":\"https://21.img.avito.st/image/1/XFCv9ray8Ln5U3q-m_cJGEhV8L8PV_I\",\"240x180\":\"https://21.img.avito.st/image/1/XFCv9ray8Ln5Vhq5m_cJGEhV8L8PV_I\",\"140x105\":\"https://21.img.avito.st/image/1/XFCv9ray8LmBVyC6m_cJGEhV8L8PV_I\",\"100x75\":\"https://21.img.avito.st/image/1/XFCv9ray8LnRVGS6m_cJGEhV8L8PV_I\"},{\"432x324\":\"https://37.img.avito.st/image/1/kH4tErayPJd7t7aQARzFNsqxPJGNsz4\",\"240x180\":\"https://37.img.avito.st/image/1/kH4tErayPJd7staXARzFNsqxPJGNsz4\",\"140x105\":\"https://37.img.avito.st/image/1/kH4tErayPJcDs-yUARzFNsqxPJGNsz4\",\"100x75\":\"https://37.img.avito.st/image/1/kH4tErayPJdTsKiUARzFNsqxPJGNsz4\",\"1280x960\":\"https://37.img.avito.st/image/1/kH4tErayPJcbpb6aARzFNsqxPJGNsz4\",\"640x480\":\"https://37.img.avito.st/image/1/kH4tErayPJcbu_6SARzFNsqxPJGNsz4\"},{\"640x480\":\"https://67.img.avito.st/image/1/Uo-AT7ay_ma25jxjqBYHx2fs_mAg7vw\",\"432x324\":\"https://67.img.avito.st/image/1/Uo-AT7ay_mbW6nRhqBYHx2fs_mAg7vw\",\"240x180\":\"https://67.img.avito.st/image/1/Uo-AT7ay_mbW7xRmqBYHx2fs_mAg7vw\",\"140x105\":\"https://67.img.avito.st/image/1/Uo-AT7ay_mau7i5lqBYHx2fs_mAg7vw\",\"100x75\":\"https://67.img.avito.st/image/1/Uo-AT7ay_mb-7WplqBYHx2fs_mAg7vw\",\"1280x960\":\"https://67.img.avito.st/image/1/Uo-AT7ay_ma2-HxrqBYHx2fs_mAg7vw\"}],\"price\":{\"title\":\"Цена\",\"value\":\"22 500\",\"value_signed\":\"22 500 ₽\",\"metric\":\"₽\"},\"seller\":{\"title\":\"Частное лицо\",\"name\":\"Александр\",\"registrationTime\":1615447591,\"connection\":{\"title\":\"Подтверждён\",\"sources\":[{\"type\":\"phone\"},{\"type\":\"email\"}]},\"link\":\"ru.avito://1/user/profile?userKey=bd43420b6f8f2fd4275f038454bf62f2&context=H4sIAAAAAAAAA0u0MrKqLgYSSpkpStaZVkaGxqam5mYWRqbWxVbGVkrFRclKQJYJUL4kNVfJuhYAVXiznTEAAAA\",\"images\":{\"1024x1024\":\"https://06.img.avito.st/avatar/social/1024x1024/10670380406.jpg\",\"64x64\":\"https://06.img.avito.st/avatar/social/64x64/10670380406.jpg\",\"96x96\":\"https://06.img.avito.st/avatar/social/96x96/10670380406.jpg\",\"128x128\":\"https://06.img.avito.st/avatar/social/128x128/10670380406.jpg\",\"192x192\":\"https://06.img.avito.st/avatar/social/192x192/10670380406.jpg\",\"256x256\":\"https://06.img.avito.st/avatar/social/256x256/10670380406.jpg\"},\"postfix\":\"Частное лицо\",\"userHashId\":\"200088569\",\"online\":false,\"replyTime\":{\"category\":3,\"text\":\"Отвечает за несколько часов\"},\"isVerified\":false,\"subscribeInfo\":{\"isSubscribed\":false},\"userHash\":\"bd43420b6f8f2fd4275f038454bf62f2\"},\"isFavorite\":false,\"stats\":{\"views\":{\"today\":45,\"total\":605}},\"contacts\":{\"list\":[{\"type\":\"phone\",\"value\":{\"title\":\"Позвонить\",\"uri\":\"ru.avito://1/phone/get?itemId=2135576825\"}},{\"type\":\"messenger\",\"value\":{\"title\":\"Написать\",\"uri\":\"ru.avito://1/item/channel/create?itemId=2135576825\"}}]},\"firebaseParams\":{\"itemID\":\"2135576825\",\"itemPrice\":\"22500\",\"withDelivery\":\"1\",\"manufacturer\":\"ASUS\",\"type_of_trade\":\"Продаю своё\",\"userAuth\":\"1\",\"userId\":\"160068181\",\"isShop\":\"0\",\"isASDClient\":\"0\",\"vertical\":\"GENERAL\",\"categoryId\":\"98\",\"categorySlug\":\"noutbuki\",\"microCategoryId\":\"46\",\"locationId\":\"648760\"},\"deliveryC2C\":{\"actions\":{\"primary\":{\"title\":\"Купить с доставкой\",\"uri\":\"ru.avito://1/delivery/order/create?itemId=2135576825&source=item\"},\"secondary\":{\"title\":\"Об Авито Доставке\",\"uri\":\"ru.avito://1/webview?url=https%3A%2F%2Fm.avito.ru%2Fdostavka%3FisApp%3D1%23buyer\"}}},\"needToCheckCreditInfo\":true,\"adjustParams\":{\"categoryId\":\"98\",\"vertical\":\"GENERAL\",\"microCategoryId\":\"46\"},\"needToCheckSimilarItems\":true,\"safeDeal\":{\"actions\":{\"orderTypes\":[\"delivery\"],\"buyButton\":{\"title\":\"Купить с доставкой\",\"icon\":\"delivery\",\"deepLink\":\"ru.avito://1/safeDeal/item/orderTypes/select?itemId=2135576825&displaying=delivery&available=delivery&style=modal\"},\"aboutLabel\":{\"text\":\"{{about}}\",\"attributes\":{\"about\":{\"type\":\"deepLink\",\"value\":{\"title\":\"Об Авито Доставке\",\"uri\":\"ru.avito://1/webview?url=https%3A%2F%2Fm.avito.ru%2Fdostavka%3FisApp%3D1%23buyer\"}}}}}},\"marketplaceRenameBadge\":false,\"icebreakers\":{\"texts\":[{\"id\":10701401,\"previewText\":\"Ещё продаёте?\",\"messageText\":\"Здравствуйте! Ещё продаёте?\",\"uri\":\"ru.avito://1/item/channel/create?itemId=2135576825&messageDraft=%D0%97%D0%B4%D1%80%D0%B0%D0%B2%D1%81%D1%82%D0%B2%D1%83%D0%B9%D1%82%D0%B5%21+%D0%95%D1%89%D1%91+%D0%BF%D1%80%D0%BE%D0%B4%D0%B0%D1%91%D1%82%D0%B5%3F\"},{\"id\":10701402,\"previewText\":\"Торг уместен?\",\"messageText\":\"Здравствуйте! Скажите, торг уместен?\",\"uri\":\"ru.avito://1/item/channel/create?itemId=2135576825&messageDraft=%D0%97%D0%B4%D1%80%D0%B0%D0%B2%D1%81%D1%82%D0%B2%D1%83%D0%B9%D1%82%D0%B5%21+%D0%A1%D0%BA%D0%B0%D0%B6%D0%B8%D1%82%D0%B5%2C+%D1%82%D0%BE%D1%80%D0%B3+%D1%83%D0%BC%D0%B5%D1%81%D1%82%D0%B5%D0%BD%3F\"},{\"id\":10701403,\"previewText\":\"Отправите Авито Доставкой?\",\"messageText\":\"Здравствуйте! Сможете отправить Авито Доставкой? Тут написано, как она работает: https://support.avito.ru/categories/115000474347.\",\"uri\":\"ru.avito://1/item/channel/create?itemId=2135576825&messageDraft=%D0%97%D0%B4%D1%80%D0%B0%D0%B2%D1%81%D1%82%D0%B2%D1%83%D0%B9%D1%82%D0%B5%21+%D0%A1%D0%BC%D0%BE%D0%B6%D0%B5%D1%82%D0%B5+%D0%BE%D1%82%D0%BF%D1%80%D0%B0%D0%B2%D0%B8%D1%82%D1%8C+%D0%90%D0%B2%D0%B8%D1%82%D0%BE+%D0%94%D0%BE%D1%81%D1%82%D0%B0%D0%B2%D0%BA%D0%BE%D0%B9%3F+%D0%A2%D1%83%D1%82+%D0%BD%D0%B0%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%BE%2C+%D0%BA%D0%B0%D0%BA+%D0%BE%D0%BD%D0%B0+%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%B0%D0%B5%D1%82%3A+https%3A%2F%2Fsupport.avito.ru%2Fcategories%2F115000474347.\"},{\"id\":10701404,\"previewText\":\"Когда можно посмотреть?\",\"messageText\":\"Здравствуйте! Когда можно посмотреть?\",\"uri\":\"ru.avito://1/item/channel/create?itemId=2135576825&messageDraft=%D0%97%D0%B4%D1%80%D0%B0%D0%B2%D1%81%D1%82%D0%B2%D1%83%D0%B9%D1%82%D0%B5%21+%D0%9A%D0%BE%D0%B3%D0%B4%D0%B0+%D0%BC%D0%BE%D0%B6%D0%BD%D0%BE+%D0%BF%D0%BE%D1%81%D0%BC%D0%BE%D1%82%D1%80%D0%B5%D1%82%D1%8C%3F\"}],\"contact\":\"Спросите у продавца\"},\"seo\":{\"title\":\"Asus K543UB купить в Йошкар-Оле с доставкой | Бытовая электроника | Авито\",\"description\":\"Asus K543UB: объявление о продаже с доставкой в Йошкар-Оле на Авито. Отличный ноутбук, состояние идеальное. Использовался администратором в магазине, была установлена 1Ска и автокад. Без проблем потянет игры, танки идут хорошо. Куплен год назад за 36 тысяч рублей. Продам за 22500, нужна определенная сумма наличности. Есть дорогая сумка, можно договориться с ней. Экран 15,6 дюйма, матовый Профессор Intel 4417 2,3Ггц(аналог core i3) Оперативная память 4Гб Две видеокарты, одна встроенная + игровая 2Гб GeForce Жёсткий диск 500Гб Ноутбук вернули к заводским настройками как из магазина. Полностью чи...\",\"canonicalUrl\":\"https://www.avito.ru/yoshkar-ola/noutbuki/asus_k543ub_2135576825\"},\"customerValue\":0.43,\"breadcrumbs\":[{\"name\":\"Бытовая электроника\",\"title\":\"Бытовая электроника в Йошкар-Оле\",\"url\":\"/yoshkar-ola/bytovaya_elektronika\",\"deeplink\":\"ru.avito://1/items/search?categoryId=6&locationId=648760\"},{\"name\":\"Ноутбуки\",\"title\":\"Ноутбуки в Йошкар-Оле\",\"url\":\"/yoshkar-ola/noutbuki\",\"deeplink\":\"ru.avito://1/items/search?categoryId=98&locationId=648760\"},{\"name\":\"ASUS\",\"title\":\"ASUS\",\"url\":\"/yoshkar-ola/noutbuki/asus-ASgBAgICAUSEK_i2Ag\",\"deeplink\":\"ru.avito://1/items/search?params%5B2754%5D=19900&categoryId=98&locationId=648760\"}],\"viewItemAndBuyerEventsParams\":{\"item_id\":\"2135576825\",\"item_location_id\":\"General\",\"logcat\":\"Electronics\"}}";
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return URI.create("https://m.avito.ru/api/16/items/2135576825?key=af0deccbgcgidddjgnvljitntccdduijhdinfgjgfjir");
        }

        @Override
        public HttpClient.Version version() {
            return HttpClient.Version.HTTP_1_1;
        }
    };


}
