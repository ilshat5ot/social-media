create schema if not exists security;
create table if not exists security.users
(
    id bigserial not null primary key,
    login    varchar(150) not null unique,
    password varchar(150) not null,
    email    varchar(200) not null unique
);

create schema if not exists friends;
create table friends.friendship
(
    id bigserial not null primary key,
    source_user_id  bigint not null,
    target_user_id bigint not null,
    relationship_status varchar(3) not null,
    time_of_creation timestamp not null,
    is_archive boolean not null,

    foreign key (target_user_id) references security.users(id),
    foreign key (source_user_id) references security.users(id)
);

drop table friends.friendship;
drop table security.users;

drop schema friends cascade;
drop schema security cascade;

insert into security.users (id, login, password, email)
values (1, 'ivan', '$2a$12$z/AoSewGXWJcGqEZOghy9uLsjjI0yrp9ysTyYrfONDhKdvl0pTKR6', 'ivan@mail.ru'),
       (2, 'petr', '$2a$12$hCilf6AduJmYXXA7oPHCbudrfKLwlIZ8.UYC0eaMQgeVX2tw2r2cS', 'petr@mail.ru'),
       (3, 'alexandr', '$2a$12$lcxX/yIJ4FuM/1FEwY.HHOlWT0UxGS.GMasHqaXOnQdFzO0Iie07S', 'alexandr@mail.ru'),
       (4, 'dementii', '$2a$12$0ijHNj4ybFECBem7.s/ONerzaD.SfeceBUZkN4ZNMj2l9cqdaLzCa', 'dementii@mail.ru'),
       (5, 'dmitrii', '$2a$12$S9qo5GNGZCZYAllbNwTdjei/2IvYkWz8vRTvNumilc3fvvgzMpaRG', 'dmitrii@mail.ru'),
       (6, 'oleg', '$2a$12$APGlbZEj3D5KLPLiwuW9Ge2A2Dewm4ftPuXBm9koAwvYGUB7JH5lm', 'oleg@mail.ru'),
       (7, 'viktor', '$2a$12$ps/cwlr/3x8w/cH1L99ziuIwwGF4rRraNq56FCLZ9cAQGAgufW.Ia', 'viktor@mail.ru');
	   
