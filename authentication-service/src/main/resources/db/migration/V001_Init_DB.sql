create table if not exists accounts(
    id bigint primary key generated always as identity,
    username varchar(100) not null,
    auth_id varchar not null,
    email varchar(100) not null,
    password varchar(100) not null,
    firstname varchar(100) not null,
    lastname varchar(100),
    is_locked bool default false,
    is_deleted bool default false)