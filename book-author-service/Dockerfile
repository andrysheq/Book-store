FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/book-author-service*.jar app.jar

EXPOSE 8088

ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=docker -Dserver.port=8088 -jar app.jar"]
