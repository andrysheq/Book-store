create table if not exists user_status 
(
    id integer not null,
    name varchar(255) not null unique,
    constraint user_status_pk primary key (id)
);

insert into user_status (id, name) VALUES
                                       (1,'Активен'),
                                       (3,'Заблокирован');

create table if not exists role 
(
    id integer not null, 
    name varchar(255) not null unique, 
    constraint role_pk primary key (id)
);

insert into role (id, name) VALUES
                                (1,'ADMIN'),
                                (2,'USER'),
                                (3,'MODERATOR');

create table book_status 
(
    id integer not null, 
    name varchar(255) not null unique, 
    constraint book_status_pk primary key (id)
);

insert into book_status (id, name) VALUES
                                       (1,'В наличии'),
                                       (2,'Заблокирована');

create table review_status 
(
    id integer not null, 
    name varchar(255) not null unique, 
    constraint review_status_pk primary key (id)
);
insert into review_status (id, name) VALUES
                                         (1,'На рассмотрении'),
                                         (2,'Подтвержден'),
                                         (3,'Отклонен');

create table genre 
(
    id integer not null, 
    name varchar(255) not null unique, 
    constraint genre_pk primary key (id)
);

insert into genre (id, name) VALUES
                                       (1,'Фантастика'),
                                       (2,'Детектив'),
                                       (3,'Триллер'),
                                       (4,'Роман'),
                                       (5,'Приключения'),
                                       (6,'Ужасы'),
                                       (7,'Биография'),
                                       (8,'Наука'),
                                       (9,'Художественная литература');

create sequence if not exists author_seq increment 1 minvalue 1 maxvalue 9223372036854775807 start 1 cache 1;
create table author 
(
    id bigint not null default nextval('author_seq'),
    last_name varchar(255) not null, 
    first_name varchar(255) not null, 
    email varchar(255) not null,
    patronymic varchar(255), 
    birth_date date, 
    constraint author_pk primary key (id)
);

create sequence if not exists user_seq increment 1 minvalue 1 maxvalue 9223372036854775807 start 1 cache 1;
create table "user" 
(
    id bigint not null default nextval('user_seq'),
    email varchar(255) not null unique, 
    first_name varchar(255) not null, 
    password varchar(255) not null, 
    role_id integer not null, 
    user_status_id integer not null,
    created_at timestamp not null,
    constraint user_pk primary key (id),
    constraint user_role_fk foreign key (role_id) references role (id),
    constraint user_user_status_fk foreign key (user_status_id) references user_status (id)
);

create sequence if not exists book_seq increment 1 minvalue 1 maxvalue 9223372036854775807 start 1 cache 1;
create table book 
(
    id bigint not null default nextval('book_seq'),
    title varchar(255) not null, 
    price integer      not null, 
    book_status_id integer not null, 
    author_id bigint not null,
    description varchar(512) not null, 
    genre_id integer not null,
    constraint book_book_status_fk foreign key (book_status_id) references book_status (id),
    constraint book_author_fk foreign key (author_id) references author (id),
    constraint book_genre_fk foreign key (genre_id) references genre (id),
    constraint book_pk primary key (id)
);

create sequence if not exists review_seq increment 1 minvalue 1 maxvalue 9223372036854775807 start 1 cache 1;
create table review 
(
    id bigint not null default nextval('review_seq'),
    content varchar(512) null, 
    rating integer not null, 
    created_at timestamp not null,
    status_updated_at timestamp null,
    book_id bigint not null, 
    user_id bigint not null, 
    review_status_id integer not null, 
    constraint review_pk primary key (id),
    constraint review_book_fk foreign key (book_id) references book (id),
    constraint review_user_fk foreign key (user_id) references "user" (id),
    constraint review_review_status_fk foreign key (review_status_id) references review_status (id)
);
