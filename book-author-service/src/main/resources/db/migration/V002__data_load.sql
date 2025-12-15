-- =========================================
-- СКРИПТ ЗАПОЛНЕНИЯ БД ТЕСТОВЫМИ ДАННЫМИ
-- Электронный магазин книг (100K записей)
-- =========================================

-- 1. СПРАВОЧНЫЕ ТАБЛИЦЫ (базовые значения)
-- =========================================

INSERT INTO user_status (id, name) VALUES
                                   (1,'Активен'),
                                   (2,'Неактивен'),
                                   (3,'Приостановлен'),
                                   (4,'Удален');

INSERT INTO role (id, name) VALUES
                            (1,'Администратор'),
                            (2,'Пользователь'),
                            (3,'Модератор');

INSERT INTO order_status (id, name) VALUES
                                    (1,'В ожидании'),
                                    (2,'Подтвержден'),
                                    (3,'Закрыт');

INSERT INTO book_status (id, name) VALUES
                                   (1,'В наличии'),
                                   (2,'Распродано'),
                                   (3,'Предзаказ');

-- review_status: остаётся как есть
INSERT INTO review_status (id, name) VALUES
                                     (1,'На рассмотрении'),
                                     (2,'Подтвержден'),
                                     (3,'Отклонен');

-- 2. ЖАНРЫ (15 жанров для разнообразия)
-- =========================================

INSERT INTO genre (name)
WITH RECURSIVE genre_gen AS (
    SELECT 1 as id, 'Технологии' as name
    UNION ALL
    SELECT 2, 'Научная фантастика'
    UNION ALL
    SELECT 3, 'Фантастика'
    UNION ALL
    SELECT 4, 'Детектив'
    UNION ALL
    SELECT 5, 'Триллер'
    UNION ALL
    SELECT 6, 'Роман'
    UNION ALL
    SELECT 7, 'Историческая проза'
    UNION ALL
    SELECT 8, 'Приключения'
    UNION ALL
    SELECT 9, 'Ужасы'
    UNION ALL
    SELECT 10, 'Биография'
    UNION ALL
    SELECT 11, 'История'
    UNION ALL
    SELECT 12, 'Наука'
    UNION ALL
    SELECT 13, 'Саморазвитие'
    UNION ALL
    SELECT 14, 'Бизнес'
    UNION ALL
    SELECT 15, 'Художественная литература'
)
SELECT name FROM genre_gen;

-- 3. АВТОРЫ (50K авторов)
-- =========================================

INSERT INTO author (last_name, first_name, patronymic, birth_date)
SELECT
    'Author_' || LPAD(i::text, 5, '0') as last_name,
    'Name_' || LPAD((i % 1000)::text, 4, '0') as first_name,
    'Middle_' || LPAD((i % 500)::text, 4, '0') as patronymic,
    CURRENT_DATE - (random() * 30000)::integer as birth_date
FROM generate_series(1, 50000) as i;

-- 3.1 ПОЛЬЗОВАТЕЛИ (100K пользователей - стержневая таблица)
-- =========================================

INSERT INTO "user" (email, first_name, password, role_id, user_status_id)
SELECT
    'user_' || LPAD(i::text, 6, '0') || '@bookstore.com' as email,
    'UserName_' || LPAD(i::text, 6, '0') as first_name,
    'hashed_pwd_' || md5(random()::text) as password,
    -- role_id: только 1=Admin, 2=User, 3=Moderator
    (ARRAY[1, 2, 3])[((random() * 2)::integer % 3) + 1] as role_id,
    -- user_status_id: только 1=Active, 2=Inactive, 3=Suspended, 4=Deleted
    (ARRAY[1, 2, 3, 4])[((random() * 3)::integer % 4) + 1] as user_status_id
FROM generate_series(1, 100000) as i;

-- 4. КНИГИ (100K книг - стержневая таблица)
-- =========================================

INSERT INTO book (title, price, book_status_id, author_id, description, genre_id)
SELECT
    'Book_' || LPAD(i::text, 6, '0') || '_' ||
    SUBSTRING(MD5(random()::text), 1, 8) as title,
    (5 + random() * 195)::numeric(10, 2) as price,
    -- book_status_id: только 1=Available, 2=Pre-order (80% Available, 20% Pre-order)
    CASE WHEN random() < 0.8 THEN 1 ELSE 2 END as book_status_id,
    ((i - 1) % 50000) + 1 as author_id,
    'Description of book number ' || i || ' - ' ||
    SUBSTRING(MD5(random()::text), 1, 50) as description,
    ((i - 1) % 15) + 1 as genre_id
FROM generate_series(1, 100000) as i;

-- 5. ЭЛЕКТРОННЫЕ ФАЙЛЫ (для доступных книг)
-- =========================================

INSERT INTO electronic_file (file_name, storage_key, book_id)
SELECT
    'ebook_' || LPAD(b.id::text, 6, '0') || '.pdf' as file_name,
    's3://bookstore/' || MD5(random()::text) || '.pdf' as storage_key,
    b.id as book_id
FROM book b
WHERE (b.id % 3) = 0
  AND b.book_status_id = 1;  -- только доступные книги (не на предзаказ)

-- 6. ОБЗОРЫ/РЕЦЕНЗИИ (300K обзоров - стержневая таблица)
-- =========================================

INSERT INTO review (content, rating, "date", book_id, user_id, review_status_id)
SELECT
    'Review text ' || SUBSTRING(MD5(random()::text), 1, 30) || ' ...' as content,
    ((random() * 4)::integer % 5) + 1 as rating,
    CURRENT_DATE - (random() * 365)::integer as "date",
    ((i - 1) % 100000) + 1 as book_id,
    ((i - 1) % 100000) + 1 as user_id,
    -- review_status_id: 1=Pending, 2=Approved, 3=Rejected, 4=Hidden
    (ARRAY[1, 2, 3, 4])[((random() * 3)::integer % 3) + 1] as review_status_id
FROM generate_series(1, 300000) as i
ON CONFLICT (user_id, book_id) DO NOTHING;

-- 7. КОРЗИНЫ И ТОВАРЫ В КОРЗИНЕ (100K корзин)
-- =========================================

INSERT INTO cart (total_amount, user_id)
SELECT
    0::numeric(10, 2) as total_amount,
    u.id as user_id
FROM "user" u
WHERE u.id <= 100000;

-- Товары в корзинах (в среднем 3-4 товара в корзине)
INSERT INTO cart_item (quantity, cart_id, book_id)
SELECT
    1          AS quantity,
    c.id       AS cart_id,
    b.id       AS book_id
FROM cart c
         JOIN book b ON b.id = c.id
WHERE b.book_status_id = 1;
-- Вторая книга: book_id = cart_id - 1, для чётных корзин
INSERT INTO cart_item (quantity, cart_id, book_id)
SELECT
    1        AS quantity,
    c.id     AS cart_id,
    b.id     AS book_id
FROM cart c
         JOIN book b
              ON b.id = c.id - 1
WHERE c.id > 1
  AND c.id % 2 = 0           -- "иногда": только чётные корзины
  AND b.book_status_id = 1;

-- Третья книга: book_id = cart_id - 2, для корзин кратных 3
INSERT INTO cart_item (quantity, cart_id, book_id)
SELECT
    1        AS quantity,
    c.id     AS cart_id,
    b.id     AS book_id
FROM cart c
         JOIN book b
              ON b.id = c.id - 2
WHERE c.id > 2
  AND c.id % 3 = 0           -- "иногда": только корзины кратные 3
  AND b.book_status_id = 1;



-- 8. ЗАКАЗЫ И ТОВАРЫ В ЗАКАЗАХ (100K заказов - стержневая таблица)
-- =========================================

INSERT INTO "order" (name, total_amount, user_id, order_status_id)
SELECT
    'Order_' || LPAD(i::text, 6, '0') as name,
    (10 + random() * 5000)::numeric(10, 2) as total_amount,
    ((i - 1) % 100000) + 1 as user_id,
    -- order_status_id: только 1=Pending, 2=Confirmed, 3=Cancelled
    -- Распределение: 40% Pending, 50% Confirmed, 10% Cancelled
    CASE
        WHEN random() < 0.4 THEN 1
        WHEN random() < 0.9 THEN 2
        ELSE 3
        END as order_status_id
FROM generate_series(1, 100000) as i;

-- Товары в заказах (только доступные книги)
INSERT INTO order_item (quantity, order_id, book_id)
SELECT
    1        AS quantity,
    o.id     AS order_id,
    b.id     AS book_id
FROM "order" o
         JOIN book b ON b.id = o.id
WHERE b.book_status_id = 1
ON CONFLICT (order_id, book_id) DO NOTHING;
INSERT INTO order_item (quantity, order_id, book_id)
SELECT
    1        AS quantity,
    o.id     AS order_id,
    b.id     AS book_id
FROM "order" o
         JOIN book b
              ON b.id = o.id - 1
WHERE o.id > 1
  AND o.id % 2 = 0         -- иногда: только чётные
  AND b.book_status_id = 1
ON CONFLICT (order_id, book_id) DO NOTHING;
INSERT INTO order_item (quantity, order_id, book_id)
SELECT
    1        AS quantity,
    o.id     AS order_id,
    b.id     AS book_id
FROM "order" o
         JOIN book b
              ON b.id = o.id - 2
WHERE o.id > 2
  AND o.id % 3 = 0         -- иногда: только кратные 3
  AND b.book_status_id = 1
ON CONFLICT (order_id, book_id) DO NOTHING;

-- =========================================
-- ОБНОВЛЕНИЕ СУММ КОРЗИН И ЗАКАЗОВ
-- =========================================

UPDATE cart
SET total_amount = (
    SELECT COALESCE(SUM(ci.quantity * b.price), 0)
    FROM cart_item ci
             JOIN book b ON ci.book_id = b.id
    WHERE ci.cart_id = cart.id
)
WHERE id IN (SELECT id FROM cart);

UPDATE "order"
SET total_amount = (
    SELECT COALESCE(SUM(oi.quantity * b.price), 0)
    FROM order_item oi
             JOIN book b ON oi.book_id = b.id
    WHERE oi.order_id = "order".id
)
WHERE id IN (SELECT id FROM "order");

-- =========================================
-- СТАТИСТИКА ЗАГРУЗКИ
-- =========================================
SELECT 'ПОЛЬЗОВАТЕЛИ' AS section,
       COUNT(*)       AS total,
       NULL::bigint   AS sub1,
       NULL::bigint   AS sub2,
       NULL::bigint   AS sub3,
       NULL::numeric  AS avg1,
       NULL::numeric  AS avg2
FROM "user"

UNION ALL
SELECT 'КНИГИ',
       COUNT(*)                                         AS total,
       COUNT(*) FILTER (WHERE book_status_id = 1)       AS sub1,  -- available
       COUNT(*) FILTER (WHERE book_status_id = 2)       AS sub2,  -- preorder
       NULL::bigint                                     AS sub3,
       NULL::numeric,
       NULL::numeric
FROM book

UNION ALL
SELECT 'АВТОРЫ',
       COUNT(*),
       NULL::bigint,
       NULL::bigint,
       NULL::bigint,
       NULL::numeric,
       NULL::numeric
FROM author

UNION ALL
SELECT 'ЭЛЕКТРОННЫЕ ФАЙЛЫ',
       COUNT(*),
       NULL::bigint,
       NULL::bigint,
       NULL::bigint,
       NULL::numeric,
       NULL::numeric
FROM electronic_file

UNION ALL
SELECT 'РЕЦЕНЗИИ',
       COUNT(*),
       COUNT(*) FILTER (WHERE review_status_id = 1),    -- pending
       COUNT(*) FILTER (WHERE review_status_id = 2),    -- approved
       NULL::bigint,
       NULL::numeric,
       NULL::numeric
FROM review

UNION ALL
SELECT 'ЗАКАЗЫ',
       COUNT(*),
       COUNT(*) FILTER (WHERE order_status_id = 1),     -- pending
       COUNT(*) FILTER (WHERE order_status_id = 2),     -- confirmed
       COUNT(*) FILTER (WHERE order_status_id = 3),     -- cancelled
       NULL::numeric,
       NULL::numeric
FROM "order"

UNION ALL
SELECT 'ТОВАРЫ В ЗАКАЗАХ',
       COUNT(*),
       NULL::bigint,
       NULL::bigint,
       NULL::bigint,
       ROUND(AVG(quantity)::numeric, 2),                -- avg_items_per_order
       NULL::numeric
FROM order_item

UNION ALL
SELECT 'КОРЗИНЫ',
       COUNT(*),
       COUNT(*) FILTER (WHERE total_amount > 0),        -- non_empty_carts
       NULL::bigint,
       NULL::bigint,
       ROUND(AVG(total_amount)::numeric, 2),            -- avg_cart_total
       NULL::numeric
FROM cart

UNION ALL
SELECT 'ТОВАРЫ В КОРЗИНАХ',
       COUNT(*),
       NULL::bigint,
       NULL::bigint,
       NULL::bigint,
       ROUND(AVG(quantity)::numeric, 2),                -- avg_items_per_cart
       NULL::numeric
FROM cart_item

UNION ALL
SELECT 'СПРАВОЧНЫЕ ТАБЛИЦЫ',
       (SELECT COUNT(*) FROM role)
           + (SELECT COUNT(*) FROM user_status)
           + (SELECT COUNT(*) FROM order_status)
           + (SELECT COUNT(*) FROM book_status)
           + (SELECT COUNT(*) FROM review_status)
           + (SELECT COUNT(*) FROM genre)          AS total,
       (SELECT COUNT(*) FROM role)            AS sub1,  -- можно трактовать как roles
       (SELECT COUNT(*) FROM user_status)     AS sub2,  -- user_statuses
       (SELECT COUNT(*) FROM order_status)    AS sub3,  -- order_statuses
       NULL::numeric,
       NULL::numeric;
