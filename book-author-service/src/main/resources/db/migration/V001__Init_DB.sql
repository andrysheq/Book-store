CREATE TABLE author (
    id bigserial NOT NULL
        CONSTRAINT author_pkey
            PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    gender VARCHAR(255) NOT NULL,
    birth_date date NOT NULL
);

CREATE TABLE book (
    id bigserial NOT NULL
        CONSTRAINT book_pkey
            PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    page_amount bigint NOT NULL,
    user_id bigserial,
    status VARCHAR(255) NOT NULL
);

CREATE TABLE book_author (
      book_id bigint NOT NULL,
      author_id bigint NOT NULL,
      CONSTRAINT FK551i3sllw1wj7ex6nir16blsm FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE,
      CONSTRAINT FKs4xm7q8i3uxvaiswj1c35nnxw FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE
);


