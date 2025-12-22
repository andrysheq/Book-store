FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/user-moderation-service*.jar app.jar

EXPOSE 8087

ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=docker -Dserver.port=8087 -jar app.jar"]
