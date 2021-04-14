## Avito item price update notify
Необходимо реализовать сервис, позволяющий следить за изменением цены любого объявления на Авито:

1. Сервис должен предоставить HTTP метод для подписки на изменение цены. На вход метод получает - ссылку на объявление, email на который присылать уведомления.
2. После успешной подписки, сервис должен следить за ценой объявления и присылать уведомления на указанный email.
3. Если несколько пользователей подписались на одно и тоже объявление, сервис не должен лишний раз проверять цену объявления.

## Требования к решению

- Необходимо проработать архитектуру сервиса и описать принципиальную схему работы в виде текста и/или диаграмм.
- Приложить фрагменты кода, решающие конкретные задачи:
  - Подписка на изменение цены
  - Отслеживание изменений цены
  - Отправка уведомления на почту
  - Работа с БД
- Язык программирования может быть Java
- Чтобы получить цену объявления, можно:
  - парсить web-страницу объявления
  - самостоятельно проанализировать трафик на мобильных приложениях или мобильном сайте и выяснить какой там API для получения информации об объявлении

## Усложнения

- Реализовать полноценный сервис, который решает поставленную задачу (сервис должен запускаться в docker-контейнере).
- Написаны тесты (постарайтесь достичь покрытия в 70% и больше).
- Подтверждение email пользователя.

#Dependencies
1) [java11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) latest
2) [Postgresql server 11](https://www.postgresql.org/download/) latest

#Preparation smtp server
1. specify smtp server: host, username and password
You can use smtp.gmail.com as smtp server.   
You can should set "spring.mail.username" and "spring.mail.password" with you "google account email" and "password" in the file following src\main\resources\application.properties.
Disable two factor authentication on your google account   https://myaccount.google.com/
Google account > Security > Less secure app access > turn on
Go to gmail.com > setting > see all settings > forwarding and pop/imap > enable imap

##Gradle usage:
# Build
1) `cd` project root
2) gradlew build
# Run
 java -jar build\libs\*.jar 

# Test
Test running with jacoco code coverage analysis

gradlew test

jacoco result available at the build/jacocoHtml/index.html

##Docker usage:
1. docker build -t {your-hub-username}/{package-name} .
2. docker run -p 8080:8080 {your-hub-username}/{package-name}

##Docker composer usage:
docker-composer up 
this command run
 one nginx instance container as load balancer at 4000 port
 one postgres server instance container  at 5432 port
 one application backend server instance 

#Scale
docker-composer up --scale app={instance count} db={instance count}

##Docs
![db-diagram](/docs/images/db-diagram.jpg)
![code-coverage](/docs/images/code-coverage-jacoco.jpg)
![endpoints](/docs/images/swagger-endpoints.jpg)


[Swagger]http://localhost:8080/swagger-ui.html