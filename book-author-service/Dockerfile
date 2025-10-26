FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy jar
COPY target/book-author-service*.jar app.jar

EXPOSE 8088

# Shell form для env pass (exec ok, but shell ensures vars)
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=docker -Dserver.port=8088 -jar app.jar"]
