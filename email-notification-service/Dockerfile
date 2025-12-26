FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/email-notification-service*.jar app.jar

EXPOSE 8085

ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=docker -Dserver.port=8085 -jar app.jar"]
