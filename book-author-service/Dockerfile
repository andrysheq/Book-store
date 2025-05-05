# syntax=docker/dockerfile:1
FROM eclipse-temurin:17-jdk-alpine

# Папка, куда будет скопировано приложение
WORKDIR /app

# Копируем собранный jar-файл
COPY target/book-author-service*.jar app.jar


# Открываем порт
EXPOSE 8081

# Команда запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
