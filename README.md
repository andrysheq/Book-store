# Остановить все
docker-compose down

# Пересобрать все образы
docker-compose build --no-cache

# Запустить все
docker-compose up -d

# Build без кэша и запуск book-author-service
docker compose build --no-cache book-author-service
docker-compose up book-author-service -d

# Build без кэша и запуск book-author-service
docker compose build --no-cache user-moderation-service
docker-compose up user-moderation-service -d

# Просмотр логов в реальном времени
docker logs -f user-moderation-service


