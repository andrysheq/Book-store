FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY target/*.jar /app/app.jar

EXPOSE 8089

ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-Dserver.port=8089", "-jar", "/app/app.jar"]