update user_status set name = 'x' where id = 3;

insert into user_status values (2,'Заблокирован');

update "user" set user_status_id = 2 where user_status_id = 3;

delete from user_status where id = 3;