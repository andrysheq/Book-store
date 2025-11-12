# Stage 1: build
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /src
COPY . .
RUN mvn -q -B -DskipTests package

# Stage 2: runtime
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /src/target/*.jar /app/app.jar
EXPOSE 8089
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-Dserver.port=8089","-jar","/app/app.jar"]
