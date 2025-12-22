-- V007__remove_user_references.sql
-- Удаление ссылки на таблицу пользователей из отзывов

-- Удаляем внешний ключ на таблицу user
ALTER TABLE review DROP CONSTRAINT review_user_fk;

-- Удаляем столбец user_id (заменяем на user_nickname и user_email)
ALTER TABLE review DROP COLUMN user_id;

-- Удаляем столбец user_id (заменяем на user_nickname и user_email)
ALTER TABLE review add COLUMN user_nickname VARCHAR(255) NOT NULL default 'Test User';

ALTER TABLE review add COLUMN user_email VARCHAR(255) NOT NULL default 'andrey.andreych@bk.ru';

-- Добавляем комментарии к столбцам (опционально, если используется comment)
COMMENT ON COLUMN review.user_nickname IS 'Имя пользователя-автора рецензии';
COMMENT ON COLUMN review.user_email IS 'Email пользователя-автора рецензии';
