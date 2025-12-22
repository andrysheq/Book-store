# Остановить все
docker-compose down

# Пересобрать все образы
docker-compose build --no-cache

# Запустить все
docker-compose up -d

# Build без кэша и запуск book-author-service
docker compose build --no-cache book-author-service
docker-compose up book-author-service -d

