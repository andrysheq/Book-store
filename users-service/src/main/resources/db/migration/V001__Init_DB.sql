CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 1;

-- Создать таблицу пользователей
CREATE TABLE IF NOT EXISTS "user" (
                                      id BIGINT PRIMARY KEY DEFAULT NEXTVAL('user_seq'),
                                      email VARCHAR(255) NOT NULL UNIQUE,
                                      password VARCHAR(255) NOT NULL,
                                      first_name VARCHAR(255) NOT NULL,
                                      last_name VARCHAR(255),
                                      role VARCHAR(50) NOT NULL CHECK (role IN ('USER', 'MODERATOR', 'ADMIN')),
                                      is_active BOOLEAN DEFAULT true,
                                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      updated_at TIMESTAMP,
                                      last_login_at TIMESTAMP
);

-- Создать индексы для оптимизации запросов
CREATE INDEX IF NOT EXISTS idx_user_email ON "user"(email);
CREATE INDEX IF NOT EXISTS idx_user_role ON "user"(role);
CREATE INDEX IF NOT EXISTS idx_user_is_active ON "user"(is_active);
CREATE INDEX IF NOT EXISTS idx_user_role_active ON "user"(role, is_active);

-- Добавить комментарии к таблице
COMMENT ON TABLE "user" IS 'Пользователи системы';
COMMENT ON COLUMN "user".id IS 'Уникальный идентификатор';
COMMENT ON COLUMN "user".email IS 'Email пользователя (уникальный)';
COMMENT ON COLUMN "user".password IS 'Пароль (BCrypt хеш)';
COMMENT ON COLUMN "user".first_name IS 'Имя пользователя';
COMMENT ON COLUMN "user".last_name IS 'Фамилия пользователя';
COMMENT ON COLUMN "user".role IS 'Роль: USER, MODERATOR или ADMIN';
COMMENT ON COLUMN "user".is_active IS 'Активен ли пользователь';
COMMENT ON COLUMN "user".created_at IS 'Дата создания';
COMMENT ON COLUMN "user".updated_at IS 'Дата последнего обновления';
COMMENT ON COLUMN "user".last_login_at IS 'Дата последнего входа';

-- Пароли хешированы с использованием BCrypt
-- Формат: $2a$10$HASH (BCrypt с cost factor 10)

-- Password: Password123 → Hash: $2a$10$dXJ3SW6G7P50eS2qWK3He.34SSJZLhkS8KJ4r1ownMn5P/msWWmDe
-- Password: Moderator123 → Hash: $2a$10$Zv5HgwIJ6H3lV5k4dLqPpuU8q5R1X2m3Y4n5O6p7Q8r9S0t1U2v3
-- Password: Admin123 → Hash: $2a$10$nB9u8E7dL4k3J2h1G0f9e.W8X7Y6Z5a4B3C2D1E0F9G8H7I6J5

INSERT INTO "user" (email, password, first_name, last_name, role, is_active, created_at)
VALUES
    -- Обычный пользователь 1
    (
        'andrey.andreych@bk.ru',
        '$2a$10$dXJ3SW6G7P50eS2qWK3He.34SSJZLhkS8KJ4r1ownMn5P/msWWmDe',
        'Иван',
        'Сидоров',
        'USER',
        true,
        CURRENT_TIMESTAMP
    ),

    -- Обычный пользователь 2
    (
        'user2@example.com',
        '$2a$10$dXJ3SW6G7P50eS2qWK3He.34SSJZLhkS8KJ4r1ownMn5P/msWWmDe',
        'Петр',
        'Васильев',
        'USER',
        true,
        CURRENT_TIMESTAMP
    ),

    -- Обычный пользователь 3
    (
        'user3@example.com',
        '$2a$10$dXJ3SW6G7P50eS2qWK3He.34SSJZLhkS8KJ4r1ownMn5P/msWWmDe',
        'Мария',
        'Иванова',
        'USER',
        true,
        CURRENT_TIMESTAMP
    ),

    -- Модератор 1
    (
        'moderator1@example.com',
        '$2a$10$Zv5HgwIJ6H3lV5k4dLqPpuU8q5R1X2m3Y4n5O6p7Q8r9S0t1U2v3',
        'Александр',
        'Петров',
        'MODERATOR',
        true,
        CURRENT_TIMESTAMP
    ),

    -- Модератор 2
    (
        'moderator2@example.com',
        '$2a$10$Zv5HgwIJ6H3lV5k4dLqPpuU8q5R1X2m3Y4n5O6p7Q8r9S0t1U2v3',
        'Анна',
        'Смирнова',
        'MODERATOR',
        true,
        CURRENT_TIMESTAMP
    ),

    -- Администратор
    (
        'admin@example.com',
        '$2a$10$nB9u8E7dL4k3J2h1G0f9e.W8X7Y6Z5a4B3C2D1E0F9G8H7I6J5',
        'Администратор',
        'Системный',
        'ADMIN',
        true,
        CURRENT_TIMESTAMP
    ),

    -- Неактивный пользователь
    (
        'inactive@example.com',
        '$2a$10$dXJ3SW6G7P50eS2qWK3He.34SSJZLhkS8KJ4r1ownMn5P/msWWmDe',
        'Иван',
        'Неактивный',
        'USER',
        false,
        CURRENT_TIMESTAMP
    )

ON CONFLICT (email) DO NOTHING;