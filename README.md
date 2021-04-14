
1. specify smtp server: host, username and password
You can use smtp.gmail.com as smtp server.   
You can should set "spring.mail.username" and "spring.mail.password" with you "google account email" and "password" in the file following src\main\resources\application.properties.
Disable two factor authentication on your google account   https://myaccount.google.com/
Google account > Security > Less secure app access > turn on
Go to gmail.com > setting > see all settings > forwarding and pop/imap > enable imap

2. Build and run 
Gradle usage:
Requirement java 11, postgresql 11, optional docker

1. set JAVA_HOME to java 11 installation path
2. run:  gradlew build
3. run: java -jar build\libs\*.jar 

Test running with jacoco code coverage analysis: gradlew test
jacoco result available at the build/jacocoHtml/index.html

Docker usage:
1. docker build -t {your-hub-username}/{package-name} .
2. docker run -p 8080:8080 {your-hub-username}/{package-name}

Docker composer usage:
docker-composer up or docker-composer up --scale app={instance count}

http://localhost:8080/swagger-ui.html Swagger
[Swagger]http://localhost:8080/swagger-ui.html