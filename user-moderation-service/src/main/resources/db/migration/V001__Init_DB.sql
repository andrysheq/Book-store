create table if not exists user_status 
(
    id integer not null,
    name varchar(255) not null unique,
    constraint user_status_pk primary key (id)
);

insert into user_status (id, name) VALUES
                                       (1,'Активен'),
                                       (3,'Заблокирован');

create sequence if not exists user_seq increment 1 minvalue 1 maxvalue 9223372036854775807 start 1 cache 1;
create table "user" 
(
    id bigint not null default nextval('user_seq'),
    email varchar(255) not null unique, 
    first_name varchar(255) not null,
    user_status_id integer not null,
    created_at timestamp not null,
    status_updated_at timestamp null,
    constraint user_pk primary key (id),
    constraint user_user_status_fk foreign key (user_status_id) references user_status (id)
);
