CREATE TABLE IF NOT EXISTS review_rejection_reason (
    id INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT review_rejection_reason_pk PRIMARY KEY (id)
);

INSERT INTO review_rejection_reason (id, name) VALUES
   (1, 'Спам'),
   (2, 'Оскорбительный контент'),
   (3, 'Содержание не относится к книге'),
   (4, 'Ненормативная лексика'),
   (5, 'Другое');

CREATE TABLE IF NOT EXISTS book_blocking_reason (
    id INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT book_blocking_reason_pk PRIMARY KEY (id)
);

INSERT INTO book_blocking_reason (id, name) VALUES
    (1, 'Книга запрещена на территории РФ'),
    (2, 'Нарушение авторских прав'),
    (3, 'Спам'),
    (4, 'Дублирующаяся запись'),
    (5, 'Технические проблемы'),
    (6, 'Нарушение политики платформы'),
    (7, 'По запросу автора'),
    (8, 'Другое');

ALTER TABLE review
    ADD COLUMN IF NOT EXISTS rejection_reason_id INTEGER null,
    ADD COLUMN IF NOT EXISTS rejection_comment VARCHAR(512) null,
    ADD CONSTRAINT review_rejection_reason_fk FOREIGN KEY (rejection_reason_id)
        REFERENCES review_rejection_reason(id);

ALTER TABLE book
    ADD COLUMN IF NOT EXISTS book_blocking_reason_id INTEGER null,
    ADD COLUMN IF NOT EXISTS book_blocking_comment VARCHAR(512) null,
    ADD CONSTRAINT book_blocking_reason_fk FOREIGN KEY (book_blocking_reason_id)
        REFERENCES book_blocking_reason(id);

INSERT INTO review (id, content, rating, created_at, status_updated_at, book_id, user_id, review_status_id, rejection_reason_id, rejection_comment) VALUES
    (11, 'Это спам и мусор!!! Не качество!!! Обман!!!', 1, '2025-12-05 18:00:00', '2025-12-05 19:30:00', 1, 11, 3, 1, 'Множество восклицательных знаков, характерный спам'),
    (12, 'Книга содержит неприемлемый контент', 1, '2025-12-04 10:00:00', '2025-12-04 11:00:00', 11, 9, 3, 2, 'Оскорбительные выражения в отношении автора'),
    (13, 'Оскорбительные комментарии о авторе...', 1, '2025-12-03 16:20:00', '2025-12-03 17:15:00', 2, 10, 3, 5, 'Ненормативная лексика - не допускается на платформе');