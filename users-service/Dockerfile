FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/users-service*.jar app.jar

EXPOSE 8089

ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=docker -Dserver.port=8089 -jar app.jar"]